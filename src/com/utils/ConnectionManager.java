package com.utils;

import com.entities.AutoParte;
import com.entities.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.Map;

import com.entities.OrdenTrabajo;

public class ConnectionManager {
	// jdbc:+driver://+nombreDeServidor;/:(si se pone el puerto)+puerto(a veces no es necesario);+baseDeDatos+usuario+password
	private static ConnectionManager instance;
	private Connection connection;
	private Statement statement;
	private ResultSet result;
	private final String local = FilesHelper.getLocalPath();

	private ConnectionManager() {
		connection = getConnection();
		statement = null;

		try {
			statement = connection.createStatement();
		} catch (SQLException s) {
			System.out.println(s.getMessage());
		}

		try {

			setTableAndValues();
			// statement.executeQuery("insert into master.dbo.Cliente (DNI, Nombre,
			// AutoPatente, Direccion, Provincia) values\r\n" +
			// "(38616178, 'Nicolas', 'ABC-123', 'Roca 2815', 'Bs As')");

			result = statement.executeQuery("select * from master.dbo.Cliente");

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

	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	public void insertClient(Cliente cliente) {

	}

	public static void main(String[] args) {
		// Esto es una declaración
		// Statement statement = null;

		ConnectionManager con = ConnectionManager.getInstance();

		// ResultSet devuelve un Statement

		// ConnectionManager connection = getConnection();

		// try {
		// statement = (Statement) connection.createStatement();
		// } catch (Exception e) {
		// e.getMessage();
		// }
		//
		// System.out.println(connection);

	}

	private Connection getConnection() {
		String ip = getIp();
		Connection connection = null;
		try {
			// Esto es lo mismo que hacer "import com.miscrosoft...", pero hay que hacerlo porque sino no funciona, este programa
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(ip);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}
	
	public void updateOrder(OrdenTrabajo ot, AutoParte ap) {
		executeQuery(String.format("update master.dbo.OrdenTrabajo set CantidadHoras = %d, IDRepuestoUtilizado = %d where id = %d;", ot.getHorasTrabajadas(), ap.getId(), ot.getID()));
	}

	public void addClient(Cliente client) {
		executeQuery(String.format("insert into master.dbo.Cliente (DNI, Nombre, Direccion, Provincia) values (%d, '%s', '%s', '%s')", client.getDNI(), client.getNombre(), client.getDireccion().getDireccion(), client.getDireccion().getProvincia()));
	}

	public void updateClient(Cliente cliente) {
		executeQuery(String.format("update master.dbo.Cliente set DNI = %d, Nombre = '%s', Direccion = '%s', Provincia = '%s' where id = %d", cliente.getDNI(), cliente.getNombre(), cliente.getDireccion().getDireccion(), cliente.getDireccion().getProvincia(), cliente.getId()));
	}

	private void setTableAndValues() {
		String setUpSQLRoute = String.format("%s/setUp.sql", local);
		BufferedReader file = FilesHelper.getFileToRead(setUpSQLRoute);

		if (file != null) {
			try {
				String query = file.readLine();
				while (query != null) {
					executeQuery(query);
					query = file.readLine();
				}
				file.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private String getIp() {
		String dataBaseConfigRoute = String.format("%s/connectionConfig.ini", local);
		BufferedReader file = FilesHelper.getFileToRead(dataBaseConfigRoute);
		Map dataMap = FilesHelper.getDataFromIniFile(file);
		String ip = String.format("jdbc:%s:%s:%s;databaseName=%s;user=%s;password=%s", dataMap.get("driver"), dataMap.get("serverName"), dataMap.get("port"), dataMap.get("dataBaseName"), dataMap.get("userName"), dataMap.get("password"));

		return ip;
	}

	private void executeQuery(String query) {
		try {
			statement.executeQuery(query);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
