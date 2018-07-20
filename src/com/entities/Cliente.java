package com.entities;

import com.utils.Util;

import java.util.ArrayList;

public class Cliente {
    // Todo usar ID en vez de DNI, porque puede ser duplicado
    private static int genericId = 0;
    private int id;
    private int dni;
    private String nombre;
    private Direccion direccion;
    private ArrayList<Vehiculo> vehiculos;

    public Cliente(String nombre, int dni, Direccion direccion) {
        genericId = Util.autoincrement(genericId);
        this.id = genericId;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.vehiculos = new ArrayList<>();
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

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
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
