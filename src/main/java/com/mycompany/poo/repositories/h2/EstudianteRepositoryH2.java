/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.repositories.interfaces.IRepositoryUpdatable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danal
 */

public class EstudianteRepositoryH2 implements IRepositoryUpdatable<Estudiante> {

    
    private final Connection connection;
    
    public EstudianteRepositoryH2(Connection connection1) {
        this.connection = connection1;
    }

    @Override
    public void update(Estudiante estudiante) {
        String sql = "UPDATE estudiante SET nombre = ?, apellido = ?, codigo = ?, programa_id = ?, direccion = ? WHERE id_estudiante = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getApellido());
            statement.setDouble(3, estudiante.getCodigo());
            statement.setInt(4, estudiante.getPrograma().getId_programa());
            statement.setString(5, estudiante.getDireccion().getDireccion());
            statement.setInt(6, estudiante.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void create(Estudiante estudiante) {
        String sql = "INSERT INTO estudiante (id_estudiante, nombre, apellido, codigo, programa_id, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, estudiante.getId());
            statement.setString(2, estudiante.getNombre());
            statement.setString(3, estudiante.getApellido());
            statement.setDouble(4, estudiante.getCodigo());
            statement.setInt(5, estudiante.getPrograma().getId_programa());
            statement.setString(6, estudiante.getDireccion().getDireccion());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void delete(Estudiante estudiante) {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, estudiante.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void read() {
    String sql = "SELECT * FROM estudiante";
    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            int idEstudiante = resultSet.getInt("id_estudiante");
            String nombre = resultSet.getString("nombre");
            String apellido = resultSet.getString("apellido");
            double codigo = resultSet.getDouble("codigo");
            int idPrograma = resultSet.getInt("programa_id");
            String direccion = resultSet.getString("direccion");
            System.out.println("ID: " + idEstudiante + ",Nombre: " + nombre + ", Apellido: " + apellido + ", Código: " + codigo + ", ID del Programa: " + idPrograma + ", Dirección: " + direccion);
        }
    } catch (SQLException ex) {
        Logger.getLogger(EstudianteRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
