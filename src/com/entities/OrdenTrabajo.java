package com.entities;

import java.math.BigDecimal;
import com.utils.Util;

public class OrdenTrabajo {
    private static int genericId = 0;
    private int id;
    private String fechaInicio;
    private String fechaFin;
    private Estado estado;
    private int DNICliente;
    private int DNIempleado;
    private String patente;
    private String marca;
    private String modelo;
    private String descripcion;
    private BigDecimal total;

    public OrdenTrabajo(int DNICliente, int DNIEmpleado, String marca, String modelo, String patente, String description) {
        genericId = Util.autoincrement(genericId);
        this.id = genericId;
        fechaInicio = Util.getCurrentTime();
        estado = Estado.PENDING;
        this.DNICliente = DNICliente;
        this.DNIempleado = DNIEmpleado;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = description;
    }

    public int getDNICliente() {
        return DNICliente;
    }

    public int getID() {
        return id;
    }
    
    public void setTotal(BigDecimal total) {
    	this.total = total;
    }
    
    public BigDecimal getTotal() {
    	return total;
    }

    public void setEstado(Estado state) {
        this.estado = state;
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

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaFin() {
        fechaFin = Util.getCurrentTime();
    }

    public String getFechaFin() {
        return fechaFin;
    }

}
