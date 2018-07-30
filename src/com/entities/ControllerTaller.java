package com.entities;

import com.customExceptionClasses.ClientNotFoundException;
import com.utils.Util;
import com.utils.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ControllerTaller {
    private String[] options = {"Menu de clientes", "Menu de ordenes de trabajo", "Generar factura", "Generar historial de 'algo'"};
    private String[] clientOptions = {"Agregar cliente", "Modificar cliente", "Eliminar cliente"};
    private String[] orderOptions = {"Agregar orden", "Modificar orden", "Cerrar Orden"};
    private static Scanner input;
    private static TallerMecanico taller;
    private Empleado emp;

    public ControllerTaller() {
        taller = TallerMecanico.getInstance();
        input = new Scanner(System.in);
    }

    public void init() {
        String name;

        do {
            System.out.print("Ingrese el nombre del taller: ");
            name = input.nextLine();
            try {
                name = Validator.isValidValue(name);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                name = null;
            }
        } while (name == null);

        taller.setNombre(name);
        Util.setUpCache();
    }

    public static void main(String[] args) {
        ControllerTaller controller = new ControllerTaller();
        controller.init();
        controller.showMenu();
        input.close();
    }

    public void showMenu() {
        System.out.printf("Bienvenido al taller: %s%n", taller.getNombre());
        int option;
        do {
            showDefaultOptions();
            option = input.nextInt();
        } while (!Validator.isValidOption(option, 4));

        this.redirectToSelectedView(option);
    }

    public void showDefaultOptions() {
        System.out.println("Por favor seleccione una opcion");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%02d -> %s%n", (i + 1), options[i]);
        }
        System.out.println("-1 -> Finalizar");
    }

    public void redirectToSelectedView(int option) {
        switch (option) {
            case 1:
                showClientMenu();
                break;
            case 2:
                showOrderMenu();
                break;
            case 3:
                generateTicket();
                break;
            case 4:
                generateFile();
                break;
            default:
                break;
        }
    }

    public void showClientMenu() {
        taller.showClientsList();

        int option;
        do {
            showClientsOptions();
            option = input.nextInt();
            // Esto se 'come' el enter
            input.nextLine();
        } while (!Validator.isValidOption(option, 3));
        redirectToClientOptions(option);
    }

    private void redirectToOrderOptions(int option) {
        switch (option) {
            case -1:
                showMenu();
                break;
            case 1:
                addOrderMenu();
                break;
            case 2:
                //modifyOrderMenu();
                break;
            case 3:
                //closeOrderMenu();
                break;
            default:
                break;
        }
    }

    private void redirectToClientOptions(int option) {
        switch (option) {
            case -1:
                showMenu();
                break;
            case 1:
                addClientMenu();
                break;
            case 2:
                modifyClientMenu();
                break;
            case 3:
                removeClientMenu();
                break;
            default:
                break;
        }
    }

    private void addClientMenu() {
        String name = null;
        int dni = 0;
        String direccion = null;
        String provincia = null;

        name = obtainValue("nombre", name);
        dni = obtainDNIValue(dni);
        direccion = obtainValue("direccion", direccion);
        provincia = obtainValue("provincia", provincia);

        emp = Util.getRandomEmployee();
        emp.agregarCliente(name, dni, direccion, provincia);
        showClientMenu();
        showClientsOptions();
    }

    private void modifyClientMenu() {
        int id;
        Cliente client = null;
        do {
            System.out.println("Ingrese el ID del cliente que desea modificar o -1 para volver:");
            id = input.nextInt();

            if (id == -1) {
                break;
            }

            try {
                ResultSet rs = taller.findClientByID(id);
                if (rs != null) {
                    rs.next();
                    client = taller.getClientesCache().get(rs.getInt("ID") - 1);
                    String name = null;
                    int dni = 0;
                    String direccion = null;
                    String provincia = null;
                    input.nextLine();
                    System.out.println("Ingrese los nuevos valores o -1 si no desea editar el campo");
                    name = obtainValue("nombre", name);
                    dni = obtainDNIValue(dni);
                    direccion = obtainValue("direccion", direccion);
                    provincia = obtainValue("provincia", provincia);
                    client.setNombre(name);
                    client.setDni(dni);
                    client.getDireccion().setDireccion(direccion);
                    client.getDireccion().setProvincia(provincia);
                    emp.modificarCliente(client);
                }
            } catch (ClientNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (client == null);

        showClientMenu();
    }

    private void removeClientMenu() {
        int id;
        boolean ok;
        do {
            System.out.println("Ingrese el ID del cliente que desea eliminar o -1 para volver:");
            id = input.nextInt();

            if (id == -1) {
                break;
            }

            try {
                emp.bajaCliente(id);
                ok = true;
            } catch (ClientNotFoundException e) {
                System.out.println(e.getMessage());
                ok = false;
            }
        } while (!ok);

        showClientMenu();
    }

    @SuppressWarnings("ParameterCanBeLocal")
    private String obtainValue(String field, String value) {
        do {
            System.out.printf("Ingrese %s: ", field);
            try {
                value = Validator.isValidValue(input.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                value = null;
            }
        } while (value == null);

        return value;
    }

    @SuppressWarnings("ParameterCanBeLocal")
    private int obtainDNIValue(int value) {
        do {
            System.out.println("Ingrese DNI: ");
            value = input.nextInt();
            input.nextLine();
        } while (value != -1 && !Validator.isValidDNI(value));

        return value;
    }

    private void showClientsOptions() {
        for (int i = 0; i < clientOptions.length; i++) {
            System.out.printf("%02d -> %s%n", (i + 1), clientOptions[i]);
        }
        System.out.println("-1 -> Volver al menu principal");
    }

    private void showOrdersOptions() {
        for (int i = 0; i < orderOptions.length; i++) {
            System.out.printf("%02d -> %s%n", (i + 1), orderOptions[i]);
        }
        System.out.println("-1 -> Volver al menu principal");
    }

    private void showOrderMenu() {
        ResultSet rs = taller.getOrdenes();
        String leftAlignFormat = "| %-3d | %-11s | %-9s | %-7s | %-10d | %-11d | %-7s | %-32s | %-15d | %-10d |%n";

        try {
            if (rs.isBeforeFirst()) {
                System.out.format("+-----+-------------+-----------+---------+------------+-------------+---------+----------------------------------+-----------------+------------+%n");
                System.out.format("| ID  | FechaInicio | FechaFin  | Estado  | DNICliente | DNIEmpleado | Patente | Descripcion                      | HorasTrabajadas | IDRepuesto |%n");
                System.out.format("+-----+-------------+-----------+---------+------------+-------------+---------+----------------------------------+-----------------+------------+%n");
                while (rs.next()) {
                    System.out.format(leftAlignFormat, rs.getInt("ID"), rs.getString("FechaInicio"), rs.getString("FechaFin"), rs.getString("Estado"), rs.getInt("DNICliente"), rs.getInt("DNIEmpleado"), rs.getString("Patente"), rs.getString("Descripcion"), rs.getInt("IDRepuestoUtilizado"));
                }
                System.out.format("+-----+-------------+-----------+---------+------------+-------------+---------+----------------------------------+-----------------+------------+%n");
            } else {
                System.out.println("No se han encontrado ordenes en el sistema");
            }
        } catch (SQLException e) {}

        int option;
        do {
            showOrdersOptions();
            option = input.nextInt();
            // Esto se 'come' el enter
            input.nextLine();
        } while (!Validator.isValidOption(option, 3));
        redirectToOrderOptions(option);
    }

    private void addOrderMenu() {
        ResultSet rs = null;
        int clientID;
        String marca = null;
        String modelo = null;
        String patente;
        String description = null;

        emp = Util.getRandomEmployee();

        taller.showClientsList();
        
        try {
            do {
                System.out.println("Seleccione un ID de cliente");
                clientID = input.nextInt();
                rs = taller.findClientByID(clientID);
            } while (!rs.isBeforeFirst());

            input.nextLine();
            System.out.println("Ingrese los datos del vehiculo");
            marca = obtainValue("marca", marca);
            modelo = obtainValue("modelo", modelo);
            do {
                System.out.print("Ingrese patente: ");
                patente = input.nextLine();
            } while (Validator.validatePatente(patente) != null);

            Vehiculo vehiculo = new Vehiculo(clientID, patente, marca, modelo);

            description = obtainValue("descripcion", description);

            emp.crearOrdenTrabajo(clientID, vehiculo, description);
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {}
    }

    public void generateTicket() {

    }

    public void generateFile() {

    }
}
