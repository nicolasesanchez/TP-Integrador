package com.utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FilesHelper {

    public static String getLocalPath() {
        // This returns the path according to the OS
        Path local = Paths.get("").toAbsolutePath();
        return local.toString();
    }

    public static BufferedReader getFileToRead(String path) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return br;
    }

    private static BufferedWriter getFileToWrite(String path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bw;
    }

    public static Map<String, String> getDataFromIniFile(BufferedReader br) throws IOException {
        Map<String, String> data = new HashMap<>();
        IniManager ini = new IniManager();

        String line = br.readLine();
        while (line != null) {
            if (ini.isValue(line)) {
                data.put(ini.getKey(line), ini.getValue(line));
            }
            line = br.readLine();
        }

        return data;
    }

    public static void createFileSCV() {
        String local = getLocalPath();
        BufferedWriter bufferedWriter = getFileToWrite(String.format("%s/historial.csv", local));
        ResultSet result = ConnectionManager.getInstance().getOrdenesCerradas();
        try {
            if (result.isBeforeFirst()) {
                bufferedWriter.write("ID;FechaInicio;FechaFin;Estado;DNICliente;DNIEmpleado;Marca;Modelo;PatenteVehiculo;Descripcion;Total");
                while (result.next()) {
                    bufferedWriter.write(String.format("%d;%s;%s;%s;%d;%d;%s;%s;%s;%s;%s", result.getInt("ID"), result.getString("FechaInicio"),
                            result.getString("FechaFin"), result.getString("Estado"),
                            result.getInt("DNICliente"), result.getInt("DNIEmpleado"),
                            String.valueOf(result.getBigDecimal("Total")), result.getString("Marca"),
                            result.getString("Modelo"), result.getString("PatenteVehiculo"),
                            result.getString("Descripcion")));
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class IniManager {
        public boolean isComment(String line) {
            return line.startsWith(";");
        }

        public boolean isSection(String line) {
            return line.startsWith("[") && line.endsWith("]");
        }

        public boolean isBlankLine(String line) {
            return line.isEmpty();
        }

        public boolean isValue(String line) {
            return !isComment(line) && !isSection(line) && !isBlankLine(line);
        }

        public String getKey(String value) {
            String key = value.split("=")[0];
            return key;
        }

        public String getValue(String val) {
            String value = val.split("=")[1];
            return value;
        }
    }

}

