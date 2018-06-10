package com.entities;

public class Direccion {
	private int numero;
	private String calle;
	private String localidad;
	private String partido;
	private String provincia;
	
	public Direccion(int numero, String calle, String localidad, String partido, String provincia) {
		this.numero = numero;
		this.calle = calle;
		this.localidad = localidad;
		this.partido = partido;
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public String getPartido() {
		return this.partido;
	}

	public String getProvincia() {
		return this.provincia;
	}

}
