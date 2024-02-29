/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Municipio;
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
public class MunicipioRepositoryH2 implements IRepositoryUpdatable<Municipio>{

    private Connection connection;

    public MunicipioRepositoryH2(Connection connection1){
        connection = connection1;
    }
    
    @Override
    public void update(Municipio municipio) {
        String sql = "UPDATE municipio SET nombre_municipio = ?, departamento_id = ? WHERE id_municipio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, municipio.getNombre_municipio());
            statement.setInt(2, municipio.getDepartamento().getId_departamento());
            statement.setInt(3, municipio.getId_municipio());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MunicipioRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void create(Municipio municipio) {
        String sql = "INSERT INTO municipio (id_municipio, nombre_municipio, departamento_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, municipio.getId_municipio());
            statement.setString(2, municipio.getNombre_municipio());
            statement.setInt(3, municipio.getDepartamento().getId_departamento());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MunicipioRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Municipio municipio) {
        String sql = "DELETE FROM municipio WHERE id_municipio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, municipio.getId_municipio());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MunicipioRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void read() {

        String sql = "SELECT * FROM municipio";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idMunicipio = resultSet.getInt("id_municipio");
                String nombre = resultSet.getString("nombre_municipio");
                int departamentoId = resultSet.getInt("departamento_id");
                System.out.println("ID Municipio: " + idMunicipio + ", Nombre: " + nombre + ", ID Departamento: " + departamentoId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MunicipioRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
