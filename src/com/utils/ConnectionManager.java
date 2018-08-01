package com.utils;

import com.entities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;

public class ConnectionManager {
	// jdbc:+driver://+nombreDeServidor;/:(si se pone el puerto)+puerto(a veces no es necesario);+baseDeDatos+usuario+password
	private static ConnectionManager instance;
	private Connection connection;
	private Statement statement;
	private final String local = FilesHelper.getLocalPath();
	private ResultSet resultSet = null;

	private ConnectionManager() {
		connection = getConnection();
		statement = null;

		try {
			statement = connection.createStatement();
		} catch (SQLException s) {
			//System.out.println(s.getMessage());
		}

		setTableAndValues();
	}

	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	public static void main(String[] args) {
		ConnectionManager con = ConnectionManager.getInstance();
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

	public void addClient(Cliente client) {
		executeQuery(String.format("insert into master.dbo.Cliente (ID, DNI, Nombre, Direccion, Provincia) values (%d, %d, '%s', '%s', '%s')", client.getId(), client.getDNI(), client.getNombre(), client.getDireccion().getDireccion(), client.getDireccion().getProvincia()));
	}

	public void updateClient(Cliente cliente) {
		executeQuery(String.format("update master.dbo.Cliente set DNI = %d, Nombre = '%s', Direccion = '%s', Provincia = '%s' where id = %d", cliente.getDNI(), cliente.getNombre(), cliente.getDireccion().getDireccion(), cliente.getDireccion().getProvincia(), cliente.getId()));
	}

	public void deleteClient(int id) {
		executeQuery(String.format("delete from master.dbo.Cliente where ID = %d", id));
	}

	public void addOrder(OrdenTrabajo ot) {
		executeQuery(String.format("insert into master.dbo.OrdenTrabajo (ID, FechaInicio, Estado, DNICliente, DNIEmpleado, Marca, Modelo, PatenteVehiculo, Descripcion)" +
				"values (%d, '%s', '%s', %d, %d, '%s', '%s', '%s', '%s')", ot.getID(), ot.getFechaInicio(), ot.getEstado(), ot.getDNICliente(), ot.getDNIEmpleado(), ot.getMarca(), ot.getModelo(), ot.getPatente(), ot.getDescripcion()));
	}

	public void updateOrder(int orderId, int repID, int horas, int cantRep) {
		executeQuery(String.format("update master.dbo.OrdenTrabajo set Estado = '%s' where ID = %d", Estado.WIP, orderId));
		executeQuery(String.format("insert into master.dbo.OrdenTrabajoRepuesto (OrdenID, RepuestoID, CantidadHoras, CantidadRepuestos) values (%d, %d, %d, %d)", orderId, repID, horas, cantRep));
	}

	public void closeOrder(int orderID, String fechaFin, BigDecimal total) {
		// Is there any way to pass a BigDecimal as String.format?
		String query = "update master.dbo.OrdenTrabajo set Estado = " + Estado.DONE + ", FechaFin = " + fechaFin + ", Total = " + total + " where ID = " + orderID;
		executeQuery(query);
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
		Map<String, String> dataMap;
		String ip = null;

		try {
			dataMap = FilesHelper.getDataFromIniFile(file);
			ip = String.format("jdbc:%s:%s:%d;databaseName=%s;user=%s;password=%s", dataMap.get("driver"), dataMap.get("serverName"), Integer.valueOf(dataMap.get("port")), dataMap.get("dataBaseName"), dataMap.get("userName"), dataMap.get("password"));
			file.close();
		} catch (IOException e) {}

		return ip;
	}

	private void executeQuery(String query) {
		try {
			statement.executeQuery(query);
		} catch(SQLException e) {}
	}

	public ResultSet getClientes() {
		return executeQueryWithReturn("select * from master.dbo.Cliente");
	}

	public ResultSet getOrdenes() {
		return executeQueryWithReturn("select * from master.dbo.OrdenTrabajo");
	}

	public ResultSet getRepuestos() {
		return executeQueryWithReturn("select * from master.dbo.Repuesto");
	}

	public ResultSet findClientByID(int id) {
		return executeQueryWithReturn(String.format("select * from master.dbo.Cliente where ID = %d", id));
	}

	public ResultSet findOrderByID(int id) {
		return executeQueryWithReturn(String.format("select * from master.dbo.OrdenTrabajo where ID = %d", id));
	}

	public ResultSet findRepuestoByID(int id) {
		return executeQueryWithReturn(String.format("select * from master.dbo.Repuesto where ID = %d", id));
	}

	private ResultSet executeQueryWithReturn(String query) {
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			resultSet = null;
		}

		return resultSet;
	}

	public Statement getTemporalStatement() throws SQLException {
		Statement st = connection.createStatement();
		return st;
	}
}
