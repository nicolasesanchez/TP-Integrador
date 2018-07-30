package com.entities;

import com.utils.Util;

import java.util.ArrayList;

public class Cliente {
    private static int genericId = 0;
    private int id;
    private int dni;
    private String nombre;
    private Direccion direccion;
    private Vehiculo vehiculo;

    public Cliente(String nombre, int dni, Direccion direccion) {
        genericId = Util.autoincrement(genericId);
        this.id = genericId;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public int getDNI() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setNombre(String name) {
        if (!name.equals("-1"))
            this.nombre = name;
    }

    public void setDni(int dni) {
        if (dni != -1)
            this.dni = dni;
    }

}
