package com.mycompany.poo.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FactoryDB {

    private static final String CONFIG_FILE = "database.properties";
    private static final String TYPE_PROPERTY = "type";
    private static FactoryDB instance;

    private FactoryDB() {
        // Constructor privado para evitar la instanciación directa
    }

    public static synchronized FactoryDB getInstance() {
        if (instance == null) {
            instance = new FactoryDB();
        }
        return instance;
    }

    public String getProperty() {
        Properties propiedades = new Properties();
        try (FileInputStream input = new FileInputStream(new File(CONFIG_FILE))) {
            propiedades.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propiedades.getProperty(TYPE_PROPERTY);
    }

    public IRepositoryFactory getDatabase() {
        return switch (this.getProperty()) {
            case "h2" -> new H2Factory();
            case "file" -> new FileFactory();
            default -> new FileFactory();
        };
    }
}
