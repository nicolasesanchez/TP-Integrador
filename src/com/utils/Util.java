package com.utils;

import com.entities.Empleado;
import com.entities.TallerMecanico;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Util {

    public static int autoincrement(int index) {
        return ++index;
    }

    public static String getCurrentTime() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        return String.format("%02d/%02d/%d", day, month, year);
    }

    public static Empleado getRandomEmployee() {
        ArrayList<Empleado> empleados = TallerMecanico.getInstance().getEmpleados();
        Empleado emp = null;
        if (empleados.size() > 0) {
            int random = (int) (Math.random() * empleados.size()) + 1;
            emp = empleados.get(random - 1);
        } else {
            System.err.println("Por alguna razón no hay empleados cargados en la base de datos");
        }

        return emp;
    }

    public static void setUpCache() {
        Empleado emp = null;
        emp = new Empleado("Valeria", 27837465);
        TallerMecanico taller = TallerMecanico.getInstance();
        taller.getEmpleados().add(emp);
        emp = new Empleado("Pablo", 30928392);
        taller.getEmpleados().add(emp);
        emp = new Empleado("Andres", 32543890);
    }

}
