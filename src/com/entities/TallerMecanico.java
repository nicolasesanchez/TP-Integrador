package com.entities;

import com.customExceptionClasses.*;
import com.utils.Conexion;
import com.utils.Validator;

import java.sql.SQLException;
import java.util.ArrayList;

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
        //Todo descomentar cuando este todo listo (o sea, nunca .__.) !!
        //base = Conexion.getInstance();
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<OrdenTrabajo> getOrdenes() {
        return ordenes;
    }

    public static TallerMecanico getInstance() {
        if (instance == null) {
            instance = new TallerMecanico();
        }
        return instance;
    }

    public void cargkarOrdenTrabajo(OrdenTrabajo ot) {//throws IllegalArgumentException {
        //if (Validator.validateOrdenTrabajo(ot) != null) {
        // Todo es necesario validar??
        ordenes.add(ot);
        //}
    }

    public void modificarOrden(OrdenTrabajo ot, int horas, AutoParte rep) throws OrdenTrabajoNotFoundException {
        int index = ordenes.indexOf(ot);
        if (index != -1) {
            OrdenTrabajo modify = ordenes.get(index);
            modify.setHorasTrabajadas(horas);
            ot.setRepuestosUtilizados(rep);
            ot.setEstado(Estado.WIP);
            try {
                base.updateOrder(ot, rep);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new OrdenTrabajoNotFoundException(String.format("The order '%d' was not found in the data base", ot.getID()));
        }
    }

    //Todo main (borrar)
    /*public static void main(String[] args) {
        TallerMecanico t = TallerMecanico.getInstance();
        try {
            OrdenTrabajo ot = new OrdenTrabajo(null, null, null, "");
            t.cargarOrdenTrabajo(ot);
            t.modificarOrden(ot, 2, null);
            System.out.println(t.getOrdenes().get(0).getHorasTrabajadas());
        } catch(OrdenTrabajoNotFoundException e){}
    }*/

    public void altaCliente(Cliente cliente) throws IllegalArgumentException {
        if (Validator.validateClient(cliente) != null) {
            clientes.add(cliente);
            try {
                base.addClient(cliente);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void bajaCliente(Cliente cliente) {
        //Todo eliminarlo de la base de datos también
        clientes.remove(cliente);
    }

    public void modificarCliente(Cliente cliente) {
        int index = clientes.indexOf(cliente);
        if (index != -1) {
            clientes.set(index, cliente);
            try {
                base.updateClient(cliente);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public ArrayList<Empleado> getEmpleados() {
        return this.empleados;
    }

    public Cliente findClient(int id) throws ClientNotFoundException {
        for (Cliente c : clientes) {
            if (id == c.getId()) {
                return c;
            }
        }
        throw new ClientNotFoundException(String.format("The client '%d' does not exists in the data base", id));
    }

}
