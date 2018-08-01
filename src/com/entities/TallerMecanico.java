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

    public ResultSet getRepuestos() {
        return base.getRepuestos();
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

    public void modificarOrden(int orderID, int repID, int horas, int cantRep) {
        ordenes.get(orderID - 1).setEstado(Estado.WIP);
        base.updateOrder(orderID, repID, horas, cantRep);
    }

    public void cerrarOrden(int orderID) throws CustomException {
        findOrderByID(orderID);
        OrdenTrabajo closeOrder = ordenes.get(orderID - 1);
        closeOrder.setEstado(Estado.DONE);
        closeOrder.setFechaFin();
        base.closeOrder(orderID, closeOrder.getFechaFin());
    }

    public void altaCliente(Cliente cliente) throws IllegalArgumentException {
        clientes.add(cliente);
        base.addClient(cliente);
    }

    public void bajaCliente(int id) throws CustomException {
        findClientByID(id);
        clientes.remove(clientes.get(id - 1));
        base.deleteClient(id);
    }

    public void modificarCliente(int id, String name, int dni, String direccion, String provincia) {
        Cliente clientEdit = clientes.get(id - 1);
        clientEdit.setNombre(name);
        clientEdit.setDni(dni);
        clientEdit.getDireccion().setDireccion(direccion);
        clientEdit.getDireccion().setProvincia(provincia);
        base.updateClient(clientEdit);
    }

    public ArrayList<Empleado> getEmpleados() {
        return this.empleados;
    }

    public ResultSet findClientByID(int id) throws CustomException {
        resultSet = base.findClientByID(id);
        validateRS(String.format("The client %d was not found in the database", id), "client");
        return resultSet;
    }

    public ResultSet findOrderByID(int id) throws CustomException {
        resultSet = base.findOrderByID(id);
        validateRS(String.format("The order with ID %d was not found in the database", id), "order");
        return resultSet;
    }

    public ResultSet findRepuestoByID(int id) throws CustomException {
        resultSet = base.findRepuestoByID(id);
        validateRS(String.format("The rep with ID %d was not found in the database", id), "rep");
        return resultSet;
    }

    private void validateRS(String message, String typeException) throws CustomException {
        try {
            if (!resultSet.isBeforeFirst()) {
                switch (typeException) {
                    case "client":
                        throw new ClientNotFoundException(message);
                    case "order":
                        throw new OrdenTrabajoNotFoundException(message);
                    case "rep":
                        throw new RepuestoNotFoundException(message);
                }
            }
        } catch (SQLException e) {
            resultSet = null;
        }
    }

    public void showClientsList() {
        resultSet = getClientes();
        String leftAlignFormat = "| %-3d | %-24s | %-9d | %-24s | %-24s |%n";

        try {
            if (resultSet.isBeforeFirst()) {
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

    public void showRepuestosList() {
        resultSet = getRepuestos();
        String leftAlignFormat = "| %-3d | %-24s | %-6d |%n";

        try {
            if (resultSet.isBeforeFirst()) {
                System.out.format("+-----+--------------------------+--------+%n");
                System.out.format("| ID  | Nombre                   | Precio |%n");
                System.out.format("+-----+--------------------------+--------+%n");
                while (resultSet.next()) {
                    System.out.format(leftAlignFormat, resultSet.getInt("ID"), resultSet.getString("Nombre"), resultSet.getDouble("Precio"));
                }

                System.out.format("+-----+--------------------------+-----------+--------------------------+--------------------------+%n");
            } else {
                System.out.println("There are no 'repuestos' in the database, that's weird");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showOrdersList() {
        resultSet = getOrdenes();
        String leftAlignFormat = "| %-3d | %-11s | %-9s | %-7s | %-10d | %-11d | %-13s | %-13s | %-9s | %-32s |%n";

        try {
            if (resultSet.isBeforeFirst()) {
                System.out.format("+-----+-------------+-----------+---------+------------+-------------+---------------+---------------+-----------+----------------------------------+%n");
                System.out.format("| ID  | FechaInicio | FechaFin  | Estado  | DNICliente | DNIEmpleado | Marca         | Modelo        | Patente   | Descripcion                      |%n");
                System.out.format("+-----+-------------+-----------+---------+------------+-------------+---------------+---------------+-----------+----------------------------------+%n");
                while (resultSet.next()) {
                    System.out.format(leftAlignFormat, resultSet.getInt("ID"), resultSet.getString("FechaInicio"),
                            resultSet.getString("FechaFin"), resultSet.getString("Estado"), resultSet.getInt("DNICliente"),
                            resultSet.getInt("DNIEmpleado"), resultSet.getString("Marca"), resultSet.getString("Modelo"),
                            resultSet.getString("PatenteVehiculo"), resultSet.getString("Descripcion"));
                }
                System.out.format("+-----+-------------+-----------+---------+------------+-------------+---------------+---------------+-----------+----------------------------------+%n");
            } else {
                System.out.println("No se han encontrado ordenes en el sistema");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
