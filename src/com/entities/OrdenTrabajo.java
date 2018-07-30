package com.entities;

import com.utils.Util;

import java.util.ArrayList;

public class OrdenTrabajo {
    private static int genericId = 0;
    private int id;
    private String fechaInicio;
    private String fechaFin;
    private Estado estado;
    private int DNICliente;
    private int DNIempleado;
    private int horasTrabajadas;
    private ArrayList<AutoParte> repuestosUtilizados;
    // Datos obtenidos a partir del objeto vehículo
    private String patente;
    private String marca;
    private String modelo;
    private String descripcion;

    public OrdenTrabajo(int DNICliente, int DNIEmpleado, Vehiculo vehiculo, String description) {
        genericId = Util.autoincrement(id);
        this.id = genericId;
        fechaInicio = Util.getCurrentTime();
        estado = Estado.PENDING;
        this.DNICliente = DNICliente;
        this.DNIempleado = DNIEmpleado;
        patente = vehiculo.getPatente();
        marca = vehiculo.getMarca();
        modelo = vehiculo.getModelo();
        this.descripcion = description;
        horasTrabajadas = 0;
    }

    public int getDNICliente() {
        return DNICliente;
    }

    public int getID() {
        return id;
    }

    public void setEstado(Estado state) {
        this.estado = state;
    }

    public void setHorasTrabajadas(int horas) throws IllegalArgumentException {
        if (horas > 0) {
            this.horasTrabajadas += horas;
        } else {
            throw new IllegalArgumentException("The value 'horas' cannot be lower than 1");
        }
    }

    public int getHorasTrabajadas() {
        return this.horasTrabajadas;
    }

    public void setRepuestosUtilizados(AutoParte rep) {
        if (repuestosUtilizados == null) {
            this.repuestosUtilizados = new ArrayList<>();
        }
        this.repuestosUtilizados.add(rep);
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public Estado getEstado() {
        return estado;
    }

    public int getDNIEmpleado() {
        return DNIempleado;
    }

    public String getPatente() {
        return patente;
    }

}
