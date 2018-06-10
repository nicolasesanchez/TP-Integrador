package com.entities;

import com.utils.Util;

import java.util.ArrayList;

public class OrdenTrabajo {
    private static int id = 0;
    private String fechaInicio;
    private String fechaFin;
    private Estado estado;
    private Cliente cliente;
    private Empleado empleado;
    private int horasTrabajadas;
    private ArrayList<AutoParte> repuestosUtilizados;
    // Datos obtenidos a partir del objeto vehículo
    private String patente;
    private String marca;
    private String modelo;
    private String descripcion;

    public OrdenTrabajo(Cliente cliente, Empleado empleado, Vehiculo vehiculo) {
        id = Util.autoincrement(id);
        fechaInicio = Util.getCurrentTime();
        estado = Estado.PENDING;
        this.cliente = cliente;
        this.empleado = empleado;
        patente = vehiculo.getPatente();
        marca = vehiculo.getMarca();
        modelo = vehiculo.getModelo();
        descripcion = vehiculo.getDescripcion();
        horasTrabajadas = 0;
    }

    /*public static void main(String[] args) {
        Cliente c = new Cliente(null, 23678456, null);
        Vehiculo ve = new Vehiculo(c, Validator.validatePatente("asd 123"), "asd", "asd", "asdasdsa");
        OrdenTrabajo od = new OrdenTrabajo(null, null, ve);

        System.out.println(od.fechaInicio);

    }*/

    public Cliente getCliente() {
        return cliente;
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

    public void setRepuestosUtilizados(AutoParte rep) {
        if (repuestosUtilizados == null) {
            this.repuestosUtilizados = new ArrayList<>();
        }
        this.repuestosUtilizados.add(rep);
    }

}
