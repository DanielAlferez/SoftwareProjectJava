/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.repositories.interfaces.IRepository;
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
public class ProgramaRepositoryH2 implements IRepository<Programa>{

    private Connection connection;

    public ProgramaRepositoryH2(Connection connection1){
        connection = connection1;
    }

    @Override
    public void update(Programa programa) {
        String sql = "UPDATE programa SET nombre = ?, semestre = ?, direccion = ? WHERE id_programa = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, programa.getNombre());
            statement.setInt(2, programa.getSemestre());
            statement.setString(3, programa.getLugar().getDireccion());
            statement.setInt(4, programa.getId_programa());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramaRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void create(Programa programa) {
        String sql = "INSERT INTO programa (id_programa, nombre, semestre, direccion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, programa.getId_programa());
            statement.setString(2, programa.getNombre());
            statement.setInt(3, programa.getSemestre());
            statement.setString(4, programa.getLugar().getDireccion());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramaRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Programa programa) {
        String sql = "DELETE FROM programa WHERE id_programa = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, programa.getId_programa());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramaRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void read() {
        String sql = "SELECT * FROM programa";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idPrograma = resultSet.getInt("id_programa");
                String nombre = resultSet.getString("nombre");
                int semestre = resultSet.getInt("semestre");
                String direccion = resultSet.getString("direccion");
                System.out.println("ID Programa: " + idPrograma + ", Nombre: " + nombre + ", Semestre: " + semestre + ", Dirección: " + direccion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProgramaRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
