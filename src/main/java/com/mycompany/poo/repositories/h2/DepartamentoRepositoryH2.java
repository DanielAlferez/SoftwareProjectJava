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
         // No se imprimirá el detalle del error en la consola
         Logger.getLogger(DepartamentoRepositoryH2.class.getName()).log(Level.SEVERE, "Error al crear el departamento", ex);
    }
}

    @Override
    public List<Departamento> read() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT id_departamento, nombre_departamento FROM departamento";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_departamento");
                String nombre = resultSet.getString("nombre_departamento");
                Departamento departamento = new Departamento(id, nombre); // Suponiendo que tienes una clase Departamento
                departamentos.add(departamento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo básico de la excepción, puedes personalizarlo según tus necesidades
        }
        return departamentos;
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
    
    public static Departamento buscarDepartamentoPorId(Connection connection, int idDepartamento) {
    String sql = "SELECT * FROM departamento WHERE id_departamento = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, idDepartamento);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            // Suponiendo que la clase Departamento tiene un constructor apropiado
            Departamento departamento = new Departamento(resultSet.getInt("id_departamento"), resultSet.getString("nombre_departamento"));
            return departamento;
        }
    } catch (SQLException ex) {
        ex.printStackTrace(); // Manejo básico de la excepción, puedes personalizarlo según tus necesidades
    }
    return null;
}
}
