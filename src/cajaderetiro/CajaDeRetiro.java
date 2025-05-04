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

