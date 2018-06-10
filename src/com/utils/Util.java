package com.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Util {
    //private static final int MIN_DNI = 22000000;
    //private static final int MAX_DNI = 41000000;
    //private static ArrayList<Integer> DNIsGenerated = new ArrayList<>();

    public static int autoincrement(int index) {
        return ++index;
    }

    /*public static int generateRandomDNI() {
        int range = (MAX_DNI - MIN_DNI) + 1;
        int dni;

        do {
            dni = (int) (Math.random() * range) + MIN_DNI;
        } while (verifyDNI(dni));

        DNIsGenerated.add(dni);
        return dni;
    }

    private static boolean verifyDNI(int dni) {
        boolean found = false;

        for (int it : DNIsGenerated) {
            if (dni == it) {
                found = true;
                break;
            }
        }

        return found;
    }*/

    public static String getCurrentTime() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        return String.format("%02d/%02d/%d", day, month, year);
    }

}
