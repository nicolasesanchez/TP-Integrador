package com.entities;

import com.customExceptionClasses.*;
import com.utils.ConnectionManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TallerMecanico {
    private String nombre;
    private ArrayList<Empleado> empleados;
    private static ArrayList<Cliente> clientes;
    private ArrayList<OrdenTrabajo> ordenes;
    private static TallerMecanico instance;
    private ConnectionManager base;
    private ResultSet resultSet = null;

    public TallerMecanico() {
        empleados = new ArrayList<>();
        clientes = new ArrayList<>();
        ordenes = new ArrayList<>();
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

    public ArrayList<OrdenTrabajo> getOrdenesCache() {
        return ordenes;
    }

    public ResultSet getOrdenes() {
        return base.getOrdenes();
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

        try {
            if (!resultSet.isBeforeFirst()) {
                throw new OrdenTrabajoNotFoundException(String.format("The order '%d' was not found in the data base", ot.getID()));
            } else {
                int index = ordenes.indexOf(ot);
                OrdenTrabajo modify = ordenes.get(index);
                modify.setHorasTrabajadas(horas);
                modify.setRepuestosUtilizados(rep);
                if (modify.getEstado().equals("PENDING")) {
                    modify.setEstado(Estado.WIP);
                }
                base.updateOrder(modify, rep);
            }
        } catch (SQLException e) {}
    }

    public void altaCliente(Cliente cliente) throws IllegalArgumentException {
        clientes.add(cliente);
        base.addClient(cliente);
    }

    public void bajaCliente(int id) throws ClientNotFoundException {
        findClientByID(id);
        clientes.remove(clientes.get(id - 1));
        base.deleteClient(id);
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
        try {
            if (!resultSet.isBeforeFirst()) {
                ExceptionUtil.throwClientNotFoundException(String.format("The client %d was not found in the database", id));
            }
        } catch (SQLException e) {}

        return resultSet;
    }

    public static Cliente getClientByID(int id) {
        return clientes.get(id - 1);
    }

    public void showClientsList() {
        resultSet = getClientes();
        String leftAlignFormat = "| %-3d | %-24s | %-9d | %-24s | %-24s |%n";

        try {
            if (!resultSet.isBeforeFirst()) {
                System.out.format("+-----+--------------------------+-----------+--------------------------+--------------------------+%n");
                System.out.format("| ID  | Cliente                  | DNI       | Direccion                | Provincia                |%n");
                System.out.format("+-----+--------------------------+-----------+--------------------------+--------------------------+%n");
                    while (resultSet.next()) {
                        System.out.format(leftAlignFormat, resultSet.getInt("ID"), resultSet.getString("Nombre"), resultSet.getInt("DNI"), resultSet.getString("Direccion"), resultSet.getString("Provincia"));
                    }

                System.out.format("+-----+--------------------------+-----------+--------------------------+--------------------------+%n");
            } else {
                System.out.println("No se han encontrado clientes en el sistema");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
