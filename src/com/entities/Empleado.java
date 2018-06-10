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

    public void crearOrdenTrabajo() {
        //Todo utilizar FACADE??
        //Todo agregar input por consola
        Cliente c = new Cliente("Pepe", 38616178, null);
        Vehiculo v = new Vehiculo(c, "asd 123", "adasd", "sdadsa", "asdasd");
        OrdenTrabajo od = new OrdenTrabajo(c, this, v);
        taller.cargarOrdenTrabajo(od);
    }

    //Todo no es modificar, sino agregar horas de trabajo (tal vez minutos para obtener hora y minuto) y autopartes utilizadas
    public void modificarOrdenTrabajo(OrdenTrabajo ot, int horas, AutoParte autoParte) throws OrdenTrabajoNotFoundException {
        taller.modificarOrden(ot, horas, autoParte);
    }

    // Todo throws IllegalArgumentException
    public void agregarCliente(Cliente cliente) {
        // Todo agregar input por consola
        taller.altaCliente(cliente);
    }

    public void bajaCliente(Cliente cliente) {
        // Todo agregar input por consola?
        taller.bajaCliente(cliente);
    }

    public void modificarCliente(Cliente cliente) throws ClientNotFoundException {
        taller.modificarCliente(cliente);
    }
}
