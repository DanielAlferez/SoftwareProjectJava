package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.factories.ConnectionDatabase;
import com.mycompany.poo.repositories.interfaces.IRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartamentoRepositoryH2 implements IRepository<Departamento> {


    public DepartamentoRepositoryH2() {
    }

    private Connection connection;

    public DepartamentoRepositoryH2(Connection connection1){
        connection = connection1;
    }
    
    @Override
    public void create(Departamento departamento) {
        String sql = "INSERT INTO departamento (id_departamento, nombre_departamento) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departamento.getId_departamento());
            statement.setString(2, departamento.getNombre_departamento());
            statement.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(DepartamentoRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
     public void read() {
        String sql = "SELECT id_departamento, nombre_departamento FROM departamento";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_departamento");
                String nombre = resultSet.getString("nombre_departamento");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Departamento departamento) {
        String sql = "DELETE FROM departamento WHERE id_departamento = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, departamento.getId_departamento());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Departamento departamento) {
        String sql = "UPDATE departamento SET nombre_departamento = ? WHERE id_departamento = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departamento.getNombre_departamento());
            statement.setInt(2, departamento.getId_departamento());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DepartamentoRepositoryH2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
