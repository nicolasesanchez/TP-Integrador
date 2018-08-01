package com.entities;

import com.customExceptionClasses.CustomException;
import com.utils.Util;
import com.utils.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ControllerTaller {
    private String[] options = {"Menu de clientes", "Menu de ordenes de trabajo", "Ver detalle de orden", "Generar historial de 'algo'"};
    private String[] clientOptions = {"Agregar cliente", "Modificar cliente", "Eliminar cliente"};
    private String[] orderOptions = {"Agregar orden", "Agregar trabajo realizado a orden", "Cerrar Orden"};
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
                showOrdersMenu();
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
                modifyOrderMenu();
                break;
            case 3:
                // TODO cerrar orden y sumar totales
                closeOrderMenu();
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

        setEmployee();
        emp.agregarCliente(name, dni, direccion, provincia);
        showClientMenu();
    }

    private void modifyClientMenu() {
        int id;
        boolean ok = false;

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
                    emp.modificarCliente(rs.getInt("ID"), name, dni, direccion, provincia);
                    ok = true;
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                ok = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (!ok);

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
            } catch (CustomException e) {
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

    private int obtainValue(String field, int value) {
        do {
            System.out.printf("Ingerese %s: ", field);
            value = input.nextInt();
        } while(value <= 0);

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

    private void showOrdersMenu() {

        taller.showOrdersList();
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
        int clientID = 0;
        int clientDNI = 0;
        String marca = null;
        String modelo = null;
        String patente = null;
        String description = null;
        boolean ok;

        setEmployee();
        taller.showClientsList();

        do {
            try {
                System.out.print("Seleccione un ID de cliente o -1 para cancelar: ");
                clientID = input.nextInt();

                if (clientID == -1) {
                    break;
                }

                // I think i should not do this, it's filthy i guess...
                ResultSet rs = taller.findClientByID(clientID);
                rs.next();
                clientDNI = rs.getInt("DNI");
                ok = true;
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                ok = false;
            } catch (SQLException e) {
                ok = false;
            }
        } while (!ok);

        if (clientID != -1) {
            input.nextLine();
            System.out.println("Ingrese los datos del vehiculo");
            marca = obtainValue("marca", marca);
            modelo = obtainValue("modelo", modelo);
            patente = obtainValue("patente", patente);
            description = obtainValue("descripcion", description);
            emp.crearOrdenTrabajo(clientDNI, marca, modelo, patente, description);
        }

        showOrdersMenu();
    }

    private void modifyOrderMenu() {
        int orderID;
        int repuestoID;
        int horas = 0;
        int cantRepuesto = 0;
        boolean found;
        boolean ok = false;
        setEmployee();
        do {
            System.out.println("Ingrese el ID del orden a la que desea agregarle trabajo realizado o -1 para cancelar: ");
            orderID = input.nextInt();

            if (orderID == -1) {
                break;
            }

            try {
                ResultSet rs = taller.findOrderByID(orderID);
                ResultSet rsRep = null;
                if (rs != null) {
                    rs.next();
                    taller.showRepuestosList();
                    do {
                        System.out.println("Ingrese el ID del repuesto que desea agregar o -1 para cancelar: ");
                        repuestoID = input.nextInt();

                        if (repuestoID ==  -1) {
                            break;
                        }

                        orderID = rs.getInt("ID");
                        try {
                            rsRep = taller.findRepuestoByID(repuestoID);
                            found = true;
                        } catch (CustomException e) {
                            System.out.println(e.getMessage());
                            found = false;
                        }
                    } while (!found);

                    if (rsRep != null) {
                        rsRep.next();
                        repuestoID = rsRep.getInt("ID");
                        cantRepuesto = obtainValue("cantidad de repuestos", cantRepuesto);
                        horas = obtainValue("cantidad de horas", horas);
                        emp.modificarOrdenTrabajo(orderID, repuestoID, horas, cantRepuesto);
                    }

                    ok = true;
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                ok = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } while(!ok);

        showOrdersMenu();
    }

    private void closeOrderMenu() {
        int orderID;
        boolean ok;
        setEmployee();

        do {
            System.out.println("Ingrese el ID de la orden que desea cerrar o -1 para cancelar: ");
            orderID = input.nextInt();

            if (orderID == -1) {
                break;
            }

            try {
                emp.cerrarOrden(orderID);
                ok = true;
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                ok = false;
            }
        } while(!ok);
    }

    /*
     * Sets a random employee to perform the selected action
     */
    private void setEmployee() {
        emp = Util.getRandomEmployee();
    }

    public void generateTicket() {

    }

    public void generateFile() {

    }
}
