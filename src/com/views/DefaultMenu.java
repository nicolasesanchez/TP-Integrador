package com.views;

public class DefaultMenu {
    private String[] options = {"Menú de clientes", "Menú de órdenes de trabajo", "Generar factura", "Generar historial de 'algo'"};

    public void show(String name) {
        System.out.printf("Bienvenido al taller: %s%n", name);
        System.out.println("Por favor seleccione un opción");

        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d -> %s%n", (i + 1), options[i]);
        }
    }

}
