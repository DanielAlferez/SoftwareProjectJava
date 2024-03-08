/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.repositories.interfaces.IRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danal
 */
public class MunicipioRepositoryH2 implements IRepository<Municipio>{

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
    public List<Municipio> read() {
        List<Municipio> municipios = new ArrayList<>();
        String sql = "SELECT * FROM municipio";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idMunicipio = resultSet.getInt("id_municipio");
                String nombre = resultSet.getString("nombre_municipio");
                int departamentoId = resultSet.getInt("departamento_id");

                // Obtener el objeto Departamento asociado al Municipio
                Departamento departamento = DepartamentoRepositoryH2.buscarDepartamentoPorId(connection, departamentoId);

                // Construir el objeto Municipio y agregarlo a la lista
                Municipio municipio = new Municipio(idMunicipio, nombre, departamento);
                municipios.add(municipio);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo básico de la excepción, puedes personalizarlo según tus necesidades
        }
        return municipios;
    }
    
    public static Municipio buscarMunicipioPorId(Connection connection, int idMunicipio) {
        String sql = "SELECT * FROM municipio WHERE id_municipio = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idMunicipio);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Obtener el departamento asociado al municipio
                int idDepartamento = resultSet.getInt("departamento_id");
                Departamento departamento = DepartamentoRepositoryH2.buscarDepartamentoPorId(connection, idDepartamento);

                // Suponiendo que la clase Municipio tiene un constructor apropiado
                Municipio municipio = new Municipio(resultSet.getInt("id_municipio"), resultSet.getString("nombre_municipio"), departamento);
                return municipio;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo básico de la excepción, puedes personalizarlo según tus necesidades
        }
        return null;
    }
    
}
