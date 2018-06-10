package com.entities;

public class Vehiculo {
	private Cliente cliente;
	private String patente;
	private String marca;
	private String modelo;
	private String descripcion;
	
	public Vehiculo(Cliente cliente, String patente, String marca, String modelo, String descripcion) {
		this.patente = patente;
		this.marca = marca;
		this.modelo = modelo;
		this.cliente = cliente;
		addVehiculo(this);
		this.descripcion = descripcion;
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
	
	private void addVehiculo(Vehiculo vehiculo) {
		cliente.getVehiculos().add(vehiculo);
	}

}
