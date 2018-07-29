package com.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FilesHelper {

    public static void main(String[] args) {
        System.out.println(getLocalPath());
    }

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

