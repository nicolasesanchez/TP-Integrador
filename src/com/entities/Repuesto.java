package com.entities;

import com.utils.Util;

import java.math.BigDecimal;

public class Repuesto {
	private static int genericId = 0;
	private int id;
	private String nombre;
	private BigDecimal precio;

	public Repuesto(String nombre, BigDecimal precio) {
		genericId = Util.autoincrement(id);
		this.id = genericId;
		this.nombre = nombre;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}
}
