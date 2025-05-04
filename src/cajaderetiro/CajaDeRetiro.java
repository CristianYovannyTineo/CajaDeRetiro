/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cajaderetiro;

import java.util.*;

class Usuario {
    private String usuario;
    private String contraseña;
    private double saldo;

    public Usuario(String usuario, String contraseña, double saldoInicial) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.saldo = saldoInicial;
    }

    public String getUsuario() {
        return usuario;
    }

    public boolean verificarContraseña(String pass) {
        return contraseña.equals(pass);
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        saldo += monto;
    }

    public boolean retirar(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }
}

class Caja {
    
    private String nombre;
    private double monto;
    private final double MIN = 0;
    private final double MAX = 100000;

    public Caja(String nombre, double montoInicial) {
        this.nombre = nombre;
        this.monto = montoInicial;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMonto() {
        return monto;
    }

    public boolean retirar(double montoARetirar) {
        if (montoARetirar <= monto && montoARetirar >= MIN) {
            monto -= montoARetirar;
            return true;
        }
        return false;
    }

    public boolean añadir(double montoAAñadir) {
        if (monto + montoAAñadir <= MAX && montoAAñadir > 0) {
            monto += montoAAñadir;
            return true;
        }
        return false;
    }

    public void restablecer(double nuevoMonto) {
        if (nuevoMonto >= MIN && nuevoMonto <= MAX) {
            monto = nuevoMonto;
        }
    }

    public boolean estaVacia() {
        return monto <= 0;
    }
}

class RegistroRetiro {
    private String usuario;
    private String caja;
    private double monto;
    private Date fecha;

    public RegistroRetiro(String usuario, String caja, double monto) {
        this.usuario = usuario;
        this.caja = caja;
        this.monto = monto;
        this.fecha = new Date();
    }

    @Override
    public String toString() {
        return "[" + fecha + "] Usuario: " + usuario + ", Caja: " + caja + ", Monto retirado: $" + monto;
    }
}

class RegistroReposicion {
    
    private String caja;
    private double monto;
    private Date fecha;

    public RegistroReposicion(String caja, double monto) {
        this.caja = caja;
        this.monto = monto;
        this.fecha = new Date();
    }

    @Override
    public String toString() {
        return "[" + fecha + "] Caja: " + caja + ", Monto añadido: $" + monto;
    }
    
}

public class CajaDeRetiro {
      private static Scanner sc = new Scanner(System.in);
    private static Map<String, Usuario> usuarios = new HashMap<>();
    private static Caja caja1 = new Caja("Caja1", 100000);
    private static Caja caja2 = new Caja("Caja2", 100000);
    private static List<RegistroRetiro> registrosRetiros = new ArrayList<>();
    private static List<RegistroReposicion> registrosReposicion = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    registrarUsuario();
                    break;
                case "2":
                    loginAdministrador();
                    break;
                case "3":
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Administrador");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarUsuario() {
        System.out.print("1.1 Usuario: ");
        String usuario = sc.nextLine();

        if (usuarios.containsKey(usuario)) {
            System.out.println("El usuario ya existe. Intente con otro nombre.");
            return;
        }

        System.out.print("1.2 Contraseña: ");
        String contraseña = sc.nextLine();

        
        double saldoInicial = 100 + new Random().nextInt(901);
        Usuario nuevoUsuario = new Usuario(usuario, contraseña, saldoInicial);
        usuarios.put(usuario, nuevoUsuario);

        System.out.println("Usuario registrado con saldo inicial: $" + saldoInicial);

       
        menuUsuario(nuevoUsuario);
    }

    private static void menuUsuario(Usuario usuario) {
        while (true) {
            System.out.println("\n--- Usuario: " + usuario.getUsuario() + " ---");
            System.out.println("1. Ver mi saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero (máximo $2000)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Tu saldo actual es: $" + usuario.getSaldo());
                    break;
                case "2":
                    seleccionarCajaYRetirar(usuario);
                    break;
                case "3":
                    depositarDinero(usuario);
                    break;
                case "4":
                    System.out.println("Cerrando sesión de usuario...");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    
    private static void seleccionarCajaYRetirar(Usuario usuario) {
        System.out.println("Seleccione la caja para retirar dinero:");
        System.out.println("1. Caja1 (Disponible: $" + caja1.getMonto() + ")");
        System.out.println("2. Caja2 (Disponible: $" + caja2.getMonto() + ")");
        System.out.print("Opción: ");
        String opcionCaja = sc.nextLine();

        Caja cajaSeleccionada = null;
        if (opcionCaja.equals("1")) {
            cajaSeleccionada = caja1;
        } else if (opcionCaja.equals("2")) {
            cajaSeleccionada = caja2;
        } else {
            System.out.println("Caja inválida.");
            return;
        }

        retirarDinero(usuario, cajaSeleccionada);
    }

    private static void retirarDinero(Usuario usuario, Caja cajaSeleccionada) {
        System.out.print("Ingrese el monto a retirar: ");
        String inputMonto = sc.nextLine();
        double montoARetirar;

        try {
            montoARetirar = Double.parseDouble(inputMonto);
            if (montoARetirar <= 0) {
                System.out.println("El monto debe ser mayor que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }

        if (montoARetirar > usuario.getSaldo()) {
            System.out.println("No tienes suficiente saldo para retirar esa cantidad.");
            return;
        }

        if (cajaSeleccionada.estaVacia()) {
            System.out.println("La " + cajaSeleccionada.getNombre() + " no tiene dinero disponible.");
            return;
        }

        if (montoARetirar > cajaSeleccionada.getMonto()) {
            System.out.println("La " + cajaSeleccionada.getNombre() + " no tiene suficiente dinero para esta operación.");
            return;
        }

      
        boolean retiroCaja = cajaSeleccionada.retirar(montoARetirar);
        if (retiroCaja) {
            usuario.retirar(montoARetirar);
            registrosRetiros.add(new RegistroRetiro(usuario.getUsuario(), cajaSeleccionada.getNombre(), montoARetirar));
            System.out.println("Retiro exitoso. Nuevo saldo: $" + usuario.getSaldo());

            if (cajaSeleccionada.estaVacia()) {
                System.out.println("¡Atención! La " + cajaSeleccionada.getNombre() + " ya no tiene dinero.");
            }
        } else {
            System.out.println("No se pudo realizar el retiro.");
        }
    }

    private static void depositarDinero(Usuario usuario) {
        System.out.print("Ingrese monto a depositar (máximo $2000): ");
        String inputMonto = sc.nextLine();
        double montoADepositar;

        try {
            montoADepositar = Double.parseDouble(inputMonto);
            if (montoADepositar <= 0 || montoADepositar > 2000) {
                System.out.println("El monto debe ser mayor que 0 y máximo $2000.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }

        usuario.depositar(montoADepositar);
        System.out.println("Depósito exitoso. Nuevo saldo: $" + usuario.getSaldo());
    }

    private static void loginAdministrador() {
        System.out.print("2.1 Usuario: ");
        String usuario = sc.nextLine();

        System.out.print("2.2 Contraseña: ");
        String contraseña = sc.nextLine();

        if (!usuario.equals("Admin") || !contraseña.equals("Admin1")) {
            System.out.println("Usuario o contraseña incorrectos.");
            return;
        }

        menuAdministrador();
    }

    private static void menuAdministrador() {
        while (true) {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Ver monto de las cajas");
            System.out.println("2. Restablecer o añadir monto a una caja");
            System.out.println("3. Ver registros de retiros");
            System.out.println("4. Ver registros de reposición");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Caja1: $" + caja1.getMonto());
                    System.out.println("Caja2: $" + caja2.getMonto());
                    break;
                case "2":
                    menuRestablecerOAñadir();
                    break;
                case "3":
                    verRegistrosRetiros();
                    break;
                case "4":
                    verRegistrosReposicion();
                    break;
                case "5":
                    System.out.println("Saliendo del menú administrador...");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private static void menuRestablecerOAñadir() {
        System.out.print("Ingrese caja a modificar (1 para Caja1, 2 para Caja2): ");
        String cajaElegida = sc.nextLine();

        Caja cajaSeleccionada = null;
        if (cajaElegida.equals("1")) {
            cajaSeleccionada = caja1;
        } else if (cajaElegida.equals("2")) {
            cajaSeleccionada = caja2;
        } else {
            System.out.println("Caja inválida.");
            return;
        }

        System.out.println("1. Restablecer monto");
        System.out.println("2. Añadir monto");
        System.out.print("Seleccione opción: ");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                restablecerMontoCaja(cajaSeleccionada);
                break;
            case "2":
                añadirMontoCaja(cajaSeleccionada);
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void restablecerMontoCaja(Caja caja) {
        System.out.print("Ingrese nuevo monto para " + caja.getNombre() + " (0 - 100000): ");
        String montoStr = sc.nextLine();
        double nuevoMonto;
        try {
            nuevoMonto = Double.parseDouble(montoStr);
            if (nuevoMonto < 0 || nuevoMonto > 100000) {
                System.out.println("Monto fuera de rango.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }

        caja.restablecer(nuevoMonto);
        System.out.println(caja.getNombre() + " restablecida a $" + caja.getMonto());
    }

    private static void añadirMontoCaja(Caja caja) {
        System.out.print("Ingrese monto a añadir a " + caja.getNombre() + " (máximo hasta 100000): ");
        String montoStr = sc.nextLine();
        double montoAAñadir;
        try {
            montoAAñadir = Double.parseDouble(montoStr);
            if (montoAAñadir <= 0) {
                System.out.println("El monto debe ser mayor que 0.");
                return;
            }
            if (caja.getMonto() + montoAAñadir > 100000) {
                System.out.println("No se puede añadir esa cantidad porque excede el máximo de $100000.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");
            return;
        }

        boolean exito = caja.añadir(montoAAñadir);
        if (exito) {
            registrosReposicion.add(new RegistroReposicion(caja.getNombre(), montoAAñadir));
            System.out.println("Monto añadido exitosamente. Nuevo saldo de " + caja.getNombre() + ": $" + caja.getMonto());
        } else {
            System.out.println("No se pudo añadir el monto.");
        }
    }

    private static void verRegistrosRetiros() {
        if (registrosRetiros.isEmpty()) {
            System.out.println("No hay registros de retiros.");
            return;
        }
        System.out.println("--- Registros de retiros ---");
        for (RegistroRetiro r : registrosRetiros) {
            System.out.println(r);
        }
    }

    private static void verRegistrosReposicion() {
        if (registrosReposicion.isEmpty()) {
            System.out.println("No hay registros de reposición.");
            return;
        }
        System.out.println("--- Registros de reposición ---");
        for (RegistroReposicion r : registrosReposicion) {
            System.out.println(r);
        }
    }
    
}

