/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Departamento;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.mycompany.poo.repositories.interfaces.IRepository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;


/**
 *
 * @author Estudiante_MCA
 */
public class DepartamentosCreados implements IVisualizarInformacion, IRepository<Departamento> {
    private List<Departamento> lista = new ArrayList<>();
    private String fileName;
    
     public DepartamentosCreados(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public String nombreClase() {
       return getClass().getSimpleName();
    }

    @Override
    public String informacionObjeto() {
        StringBuilder info = new StringBuilder();
        
        // Obtener información sobre los atributos
        Field[] campos = getClass().getDeclaredFields();
        info.append("Atributos:\n");
        for (Field campo : campos) {
            info.append(campo.getType().getSimpleName()).append(" ").append(campo.getName()).append("\n");
        }

        // Obtener información sobre los métodos
        Method[] metodos = getClass().getDeclaredMethods();
        info.append("\nMétodos:\n");
        for (Method metodo : metodos) {
            info.append(metodo.getReturnType().getSimpleName()).append(" ").append(metodo.getName()).append("()\n");
        }

        return info.toString();
    }
    
    @Override
    public void create(Departamento nuevoDepartamento) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            // Obtener información del nuevo departamento
            int idDepartamento = nuevoDepartamento.getId_departamento();
            String nombreDepartamento = nuevoDepartamento.getNombre_departamento();

            // Escribir la información en el archivo
            writer.println(idDepartamento + "," + nombreDepartamento);

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }


     
    @Override
    public void delete(Departamento departamentoToRemove) {
        int idToRemove = departamentoToRemove.getId_departamento();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);

                if (id != idToRemove) {
                    lines.add(line);
                }
            }

            // Update the list
            lista.remove(departamentoToRemove);

            // Write back the modified content
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Departamento eliminado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }
        
    @Override
    public void read(){
        try (Scanner scanner = new Scanner(new File(fileName))) {
            System.out.println("Contenido del archivo " + fileName + ":\n");
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
            }
            System.out.println("\nFin del archivo.\n");
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void update(Departamento entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
