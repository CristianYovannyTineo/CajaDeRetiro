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
    
}

public class CajaDeRetiro {
    
}

