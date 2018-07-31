package com.entities;

public class OrdenTrabajoRepuesto {
    private int ordenID;
    private int repuestoID;
    private int cantidadHoras;
    private int cantidadRepuestos;

    public OrdenTrabajoRepuesto(int ordenID, int repuestoID, int cantidadHoras, int cantidadRepuestos) {
        this.ordenID = ordenID;
        this.repuestoID = repuestoID;
        this.cantidadHoras = cantidadHoras;
        this.cantidadRepuestos = cantidadRepuestos;
    }
}
