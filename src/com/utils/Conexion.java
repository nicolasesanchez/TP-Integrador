package com.utils;

import com.entities.AutoParte;
import com.entities.Cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.entities.OrdenTrabajo;
import com.microsoft.sqlserver.jdbc.*;

public class Conexion {
	// Todo aprender como se hacen las querys a través de esta clase u otra =(
	// jdbc:+driver://+nombreDeServidor;/:(si se pone el puerto)+puerto(a veces no
	// es necesario);+baseDeDatos+usuario+password
	// Todo debería estar seteada por defecto la 'ip'? O debería aceptar que se
	// carguen los campos uno por uno??
	// Todo conectar a la base correcta, la que esta seteada es la de Base de Datos,
	// tiene los libros
	// Todo esta 'ip' es para conectar local en sql windows
	private final String ip = "jdbc:sqlserver://localhost:1433;databaseName=myDB;integratedSecurity=true";
	// private final String ip =
	// "jdbc:sqlserver://10.1.20.15:1433;databaseName=BD21A07;user=BD21A07;password=BD21A07";
	private static Conexion instance;
	private Connection connection;
	private Statement statement;
	private ResultSet result;

	public Conexion() {
		connection = getConnection();
		statement = null;

		try {
			statement = connection.createStatement();
		} catch (SQLException s) {
			System.out.println(s.getMessage());
		}

		try {

			setTableAndValues();
			// statement.executeQuery("insert into myDB.dbo.Cliente (DNI, Nombre,
			// AutoPatente, Direccion, Provincia) values\r\n" +
			// "(38616178, 'Nicolas', 'ABC-123', 'Roca 2815', 'Bs As')");

			result = statement.executeQuery("select * from myDB.dbo.Cliente");

			if (result != null) {
				while (result.next()) {
					System.out.println(result.getString("Nombre"));
				}
			}

			/*
			 * try { result = statement.executeQuery(query); } catch (SQLException e) {
			 * System.out.println(e.getMessage()); }
			 */

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// query de prueba que funcion�
		// String query = "CREATE TABLE REGISTRATION " + "(id integer not null," +
		// "first VARCHAR(255), " + "age integer, " + "primary key (id))";
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
	
	public void updateOrder(OrdenTrabajo ot, int horas, AutoParte ap) {

	}

	private void setTableAndValues() {
		try {
			//BufferedReader br = new BufferedReader(new FileReader(new File("../../TP-Integrador/setUp.sql")));

			Path local = Paths.get("").toAbsolutePath();
			System.out.println(local.toAbsolutePath().toString());
			Path sqlPath = Paths.get("/setUp.sql");
			
			String setUpRoute = local.toString() + sqlPath.toString();
			System.out.println(setUpRoute);
			
			BufferedReader br = new BufferedReader(new FileReader(setUpRoute));
			String query = br.readLine();

			//String route = sqlPath.toAbsolutePath().toString();

			while (query != null) {

				try {
					statement.executeQuery(query);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} finally {
					query = br.readLine();
				}

			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
