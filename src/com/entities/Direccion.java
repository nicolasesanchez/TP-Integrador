package com.entities;

public class Direccion {
    private String direccion;
    private String provincia;

    public Direccion(String direccion, String provincia) {
        this.direccion = direccion;
        this.provincia = provincia;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        if (!direccion.equals("-1"))
            this.direccion = direccion;
    }

    public void setProvincia(String provincia) {
        if (!provincia.equals("-1"))
            this.provincia = provincia;
    }

}
