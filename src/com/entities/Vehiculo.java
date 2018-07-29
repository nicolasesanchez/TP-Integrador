package com.entities;

public class Vehiculo {
	private Cliente cliente;
	private String patente;
	private String marca;
	private String modelo;

	public Vehiculo(Cliente cliente, String patente, String marca, String modelo) {
		this.patente = patente;
		this.marca = marca;
		this.modelo = modelo;
		this.cliente = cliente;
		addVehiculo(this);
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

	private void addVehiculo(Vehiculo vehiculo) {
		cliente.getVehiculos().add(vehiculo);
	}

}
