package com.entities;

public class Direccion {
	private int numero;
	private String calle;
	private String provincia;
	
	public Direccion(int numero, String calle, String provincia) {
		this.numero = numero;
		this.calle = calle;
		this.provincia = provincia;
	}

	public String getProvincia() {
		return this.provincia;
	}

}
