package com.entities;

import com.utils.Util;

public class AutoParte {
	private static int id = 0;
	private String nombre;
	private double precio;
	// Todo is this necessary?
	//private int stock;
	
	public AutoParte(String nombre, double precio) {
		id = Util.autoincrement(id);
		this.nombre = nombre;
		this.precio = precio;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public String getNombre() {
		return nombre;
	}

}
