package com.entities;

import com.customExceptionClasses.*;
import com.utils.Conexion;
import com.utils.Validator;

import java.util.ArrayList;

//Todo agregar tiempo de servicio y no sólo autopartes agregadas??
public class TallerMecanico {
    private String nombre;
    private ArrayList<Empleado> empleados;
    private ArrayList<Cliente> clientes;
    private ArrayList<OrdenTrabajo> ordenes;
    private static TallerMecanico instance;
    private Conexion base;

    public TallerMecanico() {
        empleados = new ArrayList<>();
        clientes = new ArrayList<>();
        ordenes = new ArrayList<>();
        base = Conexion.getInstance();
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList getClientes() {
        return clientes;
    }

    public ArrayList getOrdenes() {
        return ordenes;
    }

    public static TallerMecanico getInstance() {
        if (instance == null) {
            instance = new TallerMecanico();
        }
        return instance;
    }

    public void cargarOrdenTrabajo(OrdenTrabajo ot) {//throws IllegalArgumentException {
        //if (Validator.validateOrdenTrabajo(ot) != null) {
        // Todo es necesario validar??
            ordenes.add(ot);
        //}
    }

    public void modificarOrden(OrdenTrabajo ot, int horas, AutoParte rep) throws OrdenTrabajoNotFoundException {
        int index = ordenes.indexOf(ot);
        if (index != -1) {
            ot.setHorasTrabajadas(horas);
            ot.setRepuestosUtilizados(rep);
            ot.setEstado(Estado.WIP);
            ordenes.set(index, ot);
        } else {
            throw new OrdenTrabajoNotFoundException(String.format("The order '%d' was not found in the data base", ot.getID()));
        }
        //Todo buscar orden y pisar la vieja con la nueva en la base de datos
    }

    // Todo por ahora van a estar harcodeados en la base de datos
    /*public void altaEmpleado(Empleado emp) {

    }*/

    public void altaCliente(Cliente cliente) throws IllegalArgumentException {
        if (Validator.validateClient(cliente) != null) {
            // Todo agregarlo también en la base de datos
            // Todo agarrar los campos del objeto dirección
            clientes.add(cliente);
        }
    }

    public void bajaCliente(Cliente cliente) {
        //Todo eliminarlo de la base de datos también
        clientes.remove(cliente);
    }

    public void modificarCliente(Cliente cliente) throws ClientNotFoundException {
        //Todo buscar cliente y pisar el viejo con el nuevo en la base de datos
        int index = clientes.indexOf(cliente);
        if (index != -1) {
            clientes.set(index, cliente);
        } else {
            //Todo revisar como crear la clase 'ClientNotFoundException' correctamente
            throw new ClientNotFoundException(String.format("The client '%d' does not exists in the data base", cliente.getDNI()));
        }
    }

}
