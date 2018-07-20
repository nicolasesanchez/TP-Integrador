package com.entities;

import com.utils.Util;

public class AutoParte {
	private static int genericId = 0;
	private int id;
	private String nombre;
	private double precio;

	public AutoParte(String nombre, double precio) {
		genericId = Util.autoincrement(id);
		this.id = genericId;
		this.nombre = nombre;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public String getNombre() {
		return nombre;
	}

}
