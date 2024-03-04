/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Lugar;
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

public class LugarRepositoryH2 implements IRepository<Lugar>{

    private Connection connection;

    public LugarRepositoryH2(Connection connection1){
        connection = connection1;
    }
    
    @Override
    public void update(Lugar lugar) {
        String sql = "UPDATE lugar SET departamento_id = ?, municipio_id = ? WHERE direccion = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, lugar.getDepartamento().getId_departamento());
            statement.setInt(2, lugar.getMunicipio().getId_municipio());
            statement.setString(3, lugar.getDireccion());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LugarRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void create(Lugar lugar) {
        String sql = "INSERT INTO lugar (direccion, departamento_id, municipio_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lugar.getDireccion());
            statement.setInt(2, lugar.getDepartamento().getId_departamento());
            statement.setInt(3, lugar.getMunicipio().getId_municipio());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LugarRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Lugar lugar) {
        String sql = "DELETE FROM lugar WHERE direccion = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lugar.getDireccion());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LugarRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void read() {
        String sql = "SELECT * FROM lugar";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String direccion = resultSet.getString("direccion");
                int departamentoId = resultSet.getInt("departamento_id");
                int municipioId = resultSet.getInt("municipio_id");
                System.out.println("Dirección: " + direccion + ", Departamento ID: " + departamentoId + ", Municipio ID: " + municipioId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LugarRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
