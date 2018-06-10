package com.entities;

import java.util.ArrayList;

public class Cliente {
	private int dni;
	private String nombre;
	private Direccion direccion;
	private ArrayList<Vehiculo> vehiculos;
	
	public Cliente(String nombre, int dni, Direccion direccion) {
		this.dni = dni;
		this.nombre = nombre;
		this.direccion = direccion;
		this.vehiculos = new ArrayList<>();
	}

	public int getDNI() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public Direccion getDireccion() {
		return direccion;
	}
	
	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}

}
