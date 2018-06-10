package com.entities;

import com.utils.Util;

public class AutoParte {
	private static int id = 0;
	private String nombre;
	private double precio;
	private int stock;
	
	public AutoParte(String nombre, double precio, int stock) {
		id = Util.autoincrement(id);
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setStock(int cant) {

	}

}
