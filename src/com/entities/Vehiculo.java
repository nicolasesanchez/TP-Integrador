package com.entities;

public class Vehiculo {
	private int DNICliente;
	private String patente;
	private String marca;
	private String modelo;

	public Vehiculo(int DNICliente, String patente, String marca, String modelo) {
		this.patente = patente;
		this.marca = marca;
		this.modelo = modelo;
		this.DNICliente = DNICliente;
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
		TallerMecanico.getClientByID(DNICliente).setVehiculo(vehiculo);
	}

}
