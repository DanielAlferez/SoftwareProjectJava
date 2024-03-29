/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.factories;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.repositories.h2.DepartamentoRepositoryH2;
import com.mycompany.poo.repositories.h2.EstudianteRepositoryH2;
import com.mycompany.poo.repositories.h2.LugarRepositoryH2;
import com.mycompany.poo.repositories.h2.MunicipioRepositoryH2;
import com.mycompany.poo.repositories.h2.ProgramaRepositoryH2;
import com.mycompany.poo.repositories.interfaces.IRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danal
 */
public class H2Factory implements IRepositoryFactory{

    public H2Factory() {
        try {
            ejecutarScriptSQL();
            System.out.println("Base de datos creada exitosamente.");
        } catch (SQLException e) {
        }
    }
    
    private void ejecutarScriptSQL() throws SQLException {
        Connection connection = ConnectionDatabase.getConnection();
        try (Statement statement = connection.createStatement()) {
            // Script SQL para crear las tablas
            String sql = "CREATE TABLE Departamento (" +
                            "id_departamento INT PRIMARY KEY, " +
                            "nombre_departamento VARCHAR(255)" +
                         ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Municipio (" +
                    "id_municipio INT PRIMARY KEY, " +
                    "nombre_municipio VARCHAR(255), " +
                    "departamento_id INT, " +
                    "FOREIGN KEY (departamento_id) REFERENCES Departamento(id_departamento) ON DELETE CASCADE" +
                  ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Lugar (" +
                    "direccion VARCHAR(255) PRIMARY KEY, " +
                    "departamento_id INT, " +
                    "municipio_id INT, " +
                    "FOREIGN KEY (departamento_id) REFERENCES Departamento(id_departamento) ON DELETE CASCADE, " +
                    "FOREIGN KEY (municipio_id) REFERENCES Municipio(id_municipio) ON DELETE CASCADE" +
                  ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Programa (" +
                    "id_programa INT PRIMARY KEY, " +
                    "nombre VARCHAR(255), " +
                    "semestre INT, " +
                    "direccion VARCHAR(255), " +
                    "FOREIGN KEY (direccion) REFERENCES Lugar(direccion) ON DELETE CASCADE" +
                  ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE Estudiante (" +
                    "id_estudiante INT PRIMARY KEY, " +
                    "nombre VARCHAR(255), " +
                    "apellido VARCHAR(255), " +
                    "codigo DOUBLE, " +
                    "programa_id INT, " +
                    "direccion VARCHAR(255), " +
                    "FOREIGN KEY (programa_id) REFERENCES Programa(id_programa) ON DELETE CASCADE, " +
                    "FOREIGN KEY (direccion) REFERENCES Lugar(direccion) ON DELETE CASCADE" +
                  ")";
            statement.executeUpdate(sql);
        }
    }
     
    @Override
    public IRepository<Departamento> createDepartamentoRepository() {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(H2Factory.class.getName()).log(Level.SEVERE, "Error al obtener la conexión", ex);
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos.", ex);
        }
        return new DepartamentoRepositoryH2(connection);
    }

    @Override
    public IRepository<Municipio> createMunicipioRepository() {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(H2Factory.class.getName()).log(Level.SEVERE, "Error al obtener la conexión", ex);
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos.", ex);
        }
        return new MunicipioRepositoryH2(connection);
    }

    @Override
    public IRepository<Lugar> createLugarRepository() {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(H2Factory.class.getName()).log(Level.SEVERE, "Error al obtener la conexión", ex);
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos.", ex);
        }
        return new LugarRepositoryH2(connection);
    }

    @Override
    public IRepository<Programa> createProgramaRepository() {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(H2Factory.class.getName()).log(Level.SEVERE, "Error al obtener la conexión", ex);
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos.", ex);
        }
        return new ProgramaRepositoryH2(connection);
    }

    @Override
    public IRepository<Estudiante> createEstudianteRepository() {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(H2Factory.class.getName()).log(Level.SEVERE, "Error al obtener la conexión", ex);
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos.", ex);
        }
        return new EstudianteRepositoryH2(connection);
    }

}
