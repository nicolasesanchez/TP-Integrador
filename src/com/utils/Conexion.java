package com.utils;

import com.entities.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.microsoft.sqlserver.jdbc.*;

public class Conexion {
	// Todo aprender como se hacen las querys a través de esta clase u otra =(
	// jdbc:+driver://+nombreDeServidor;/:(si se pone el puerto)+puerto(a veces no
	// es necesario);+baseDeDatos+usuario+password
	// Todo debería estar seteada por defecto la 'ip'? O debería aceptar que se
	// carguen los campos uno por uno??
	// Todo conectar a la base correcta, la que esta seteada es la de Base de Datos,
	// tiene los libros
	private final String ip = "jdbc:sqlserver://localhost:1433;databaseName=myDB;integratedSecurity=true";
	private static Conexion instance;
	private Connection connection;

	public Conexion() {
		connection = getConnection();
		Statement statement = null;

		try {
			statement = (Statement) connection.createStatement();
		} catch (SQLException s) {
			System.out.println(s.getMessage());
		}

		System.out.println(statement.toString());

		String query = "CREATE TABLE REGISTRATION " + "(id integer not null," + "first VARCHAR(255), " + "age integer, "
				+ "primary key (id))";

		try {
			statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Conexion getInstance() {
		if (instance == null) {
			instance = new Conexion();
		}
		return instance;
	}

	public void insertClient(Cliente cliente) {

	}

	public static void main(String[] args) {
		// Esto es una declaración
		// Statement statement = null;

		Conexion con = Conexion.getInstance();

		// ResultSet devuelve un Statement

		// Connection connection = getConnection();

		// try {
		// statement = (Statement) connection.createStatement();
		// } catch (Exception e) {
		// e.getMessage();
		// }
		//
		// System.out.println(connection);

	}

	private Connection getConnection() {
		Connection connection = null;
		try {
			// Esto es lo mismo que hacer "import com.miscrosoft...", pero hay que hacerlo
			// porque sino no funciona, este programa
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(ip);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

}
