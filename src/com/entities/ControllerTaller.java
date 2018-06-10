package com.entities;

import com.utils.Validator;
import com.views.DefaultMenu;

import java.util.Scanner;

public class ControllerTaller {
    private DefaultMenu menu;
    private static Scanner input;
    private static TallerMecanico taller;

    public ControllerTaller() {
        setUpViews();
        setUpData();
        input = new Scanner(System.in);
        taller = TallerMecanico.getInstance();
    }

    private void setUpViews() {
        menu = new DefaultMenu();
    }

    private void setUpData() {

    }

    public void init() {
        String name;

        do {
            System.out.print("Ingrese el nombre del taller: ");
            name = input.nextLine();
            try {
                name = Validator.validateValue(name);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                name = null;
            }
        } while (name == null);

        taller.setNombre(name);
    }

    public static void main(String[] args) {
        ControllerTaller controller = new ControllerTaller();
        controller.init();

        int option = controller.showMenu();

        controller.optionSelected(option);


    }

    private int showMenu() {
        menu.show(taller.getNombre());
        int option = input.nextInt();
        return option;
    }

    private void optionSelected(int option) {
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

    private void showClientMenu() {

    }

    private void showOrderMenu() {

    }

    private void generateTicket() {

    }

    private void generateFile() {

    }

}
