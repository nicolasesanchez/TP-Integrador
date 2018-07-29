package com.entities;

import com.customExceptionClasses.*;
import com.utils.ConnectionManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TallerMecanico {
    private String nombre;
    private ArrayList<Empleado> empleados;
    private ArrayList<Cliente> clientes;
    private ArrayList<OrdenTrabajo> ordenes;
    private static TallerMecanico instance;
    private ConnectionManager base;
    private ResultSet resultSet = null;

    public TallerMecanico() {
        empleados = new ArrayList<>();
        clientes = new ArrayList<>();
        ordenes = new ArrayList<>();
        //Todo descomentar cuando este todo listo (o sea, nunca .__.) !!
        base = ConnectionManager.getInstance();
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getNombre() {
        return nombre;
    }

    public ResultSet getClientes() {
        return base.getClientes();
    }

    public ArrayList<Cliente> getClientesCache() {
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

    public void cargarOrdenTrabajo(OrdenTrabajo ot) {
        ordenes.add(ot);
        base.addOrder(ot);
    }

    public void modificarOrden(OrdenTrabajo ot, int horas, AutoParte rep) throws OrdenTrabajoNotFoundException {
        resultSet = base.findOrderByID(ot.getID());

        if (resultSet != null) {
            int index = ordenes.indexOf(ot);
            OrdenTrabajo modify = ordenes.get(index);
            modify.setHorasTrabajadas(horas);
            modify.setRepuestosUtilizados(rep);
            modify.setEstado(Estado.WIP);
            base.updateOrder(modify, rep);
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
        clientes.add(cliente);
        base.addClient(cliente);
    }

    public void bajaCliente(int id) throws ClientNotFoundException {
        if (findClientByID(id) != null) {
            clientes.remove(clientes.get(id - 1));
            base.deleteClient(id);
        }
    }

    public void modificarCliente(Cliente cliente) {
        clientes.set(clientes.indexOf(cliente), cliente);
        base.updateClient(cliente);
    }

    public ArrayList<Empleado> getEmpleados() {
        return this.empleados;
    }

    public ResultSet findClientByID(int id) throws ClientNotFoundException {
        resultSet = base.findClientByID(id);
        if (resultSet == null) {
            ExceptionUtil.throwClientNotFoundException(String.format("The client %d was not found in the database", id));
        }

        return resultSet;
    }

    private Cliente findClienteByDNI(int dni) {
        for (Cliente c : clientes) {
            if (c.getDNI() == dni) {
                return c;
            }
        }
        return null;
    }

}
