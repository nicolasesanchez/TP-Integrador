package com.entities;

import com.customExceptionClasses.ClientNotFoundException;
import com.customExceptionClasses.OrdenTrabajoNotFoundException;

public class Empleado {
    private static int dni;
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

    public void crearOrdenTrabajo() {
        //Todo utilizar FACADE??
        Cliente c = new Cliente("Pepe", 38616178, null);
        Vehiculo v = new Vehiculo(c, "asd 123", "adasd", "sdadsa");
        OrdenTrabajo od = new OrdenTrabajo(c, this, v, "Soy una descripcion");
        taller.cargarOrdenTrabajo(od);
    }

    public void modificarOrdenTrabajo(OrdenTrabajo ot, int horas, AutoParte autoParte) throws OrdenTrabajoNotFoundException {
        taller.modificarOrden(ot, horas, autoParte);
    }

    public void agregarCliente(String name, int dni, String direccion, String provincia) {
        Direccion dir = new Direccion(direccion, provincia);
        Cliente client = new Cliente(name, dni, dir);
        taller.altaCliente(client);
    }

    public void bajaCliente(int id) throws ClientNotFoundException {
        taller.bajaCliente(id);
    }

    public void modificarCliente(Cliente cliente) {
        taller.modificarCliente(cliente);
    }
}
