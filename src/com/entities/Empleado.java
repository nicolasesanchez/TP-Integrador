package com.entities;

import com.customExceptionClasses.ClientNotFoundException;
import com.customExceptionClasses.OrdenTrabajoNotFoundException;

public class Empleado {
    private int dni;
    private String nombre;
    private TallerMecanico taller;

    public Empleado(String nombre, int dni) {
        this.dni = dni;
        this.nombre = nombre;
        taller = TallerMecanico.getInstance();
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getDNI() {
        return dni;
    }

    public void crearOrdenTrabajo(int DNICliente, String marca, String modelo, String patente, String description) {
        OrdenTrabajo orden = new OrdenTrabajo(DNICliente, this.getDNI(), marca, modelo, patente, description);
        taller.cargarOrdenTrabajo(orden);
    }

    public void modificarOrdenTrabajo(OrdenTrabajo ot, int horas, Repuesto repuesto) throws OrdenTrabajoNotFoundException {
        taller.modificarOrden(ot, horas, repuesto);
    }

    public void agregarCliente(String name, int dni, String direccion, String provincia) {
        Direccion dir = new Direccion(direccion, provincia);
        Cliente client = new Cliente(name, dni, dir);
        taller.altaCliente(client);
    }

    public void bajaCliente(int id) throws ClientNotFoundException {
        taller.bajaCliente(id);
    }

    public void modificarCliente(int id, String name, int dni, String direccion, String provincia) {
        taller.modificarCliente(id, name, dni, direccion, provincia);
    }
}
