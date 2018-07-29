package com.utils;

import com.entities.Cliente;
import com.entities.OrdenTrabajo;
import com.entities.TallerMecanico;

import java.util.ArrayList;

public class Validator {
    private final static String BLANK_SPACE_REGEX = "\\s*";
    private final static String PATENTE_REGEX = "[A-Z]{3} [0-9]{3}";

    public static String validateValue(String value) throws IllegalArgumentException {
        if (value == null || value.matches(BLANK_SPACE_REGEX)) {
            throw new IllegalArgumentException("The value cannot be null or blank spaces");
        } else {
            return value;
        }
    }

    public static String validatePatente(String value) {
        value = value.toUpperCase();
        if (validateValue(value) != null && value.matches(PATENTE_REGEX)) {
            return value.replace(" ", "-");
        } else {
            throw new IllegalArgumentException("The value contains invalid characters");
        }
    }

    public static Cliente validateClient(Cliente client) throws IllegalArgumentException {
        if (client.getNombre().matches(BLANK_SPACE_REGEX)) {
            throw new IllegalArgumentException("The field 'Nombre' cannot be empty");
        } else if (validateDNI(client.getDNI())) {
            throw new IllegalArgumentException("The value of 'DNI' is already in use by other client");
        } else {
            return client;
        }
    }

    public static OrdenTrabajo validateOrdenTrabajo(OrdenTrabajo ot) throws IllegalArgumentException {


        return ot;
    }

    private static boolean validateDNI(int dni) {
        ArrayList<Cliente> clientes = TallerMecanico.getInstance().getClientesCache();
        boolean found = false;

        for (Cliente cliente : clientes) {
            if (dni == cliente.getDNI()) {
                found = true;
                break;
            }
        }

        return found;
    }

    public static boolean isValidOption(int option, int max) {
        return (option > 0 && option <= max) || option == -1;
    }

    public static boolean isValidDNI(int dni) {
        return dni > 0 && String.valueOf(dni).length() == 8;
    }

}