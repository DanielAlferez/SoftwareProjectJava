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
import java.io.FileReader;


/**
 *
 * @author Estudiante_MCA
 */
public class DepartamentosCreados implements IVisualizarInformacion, IRepository<Departamento> {
    private final List<Departamento> lista = new ArrayList<>();
    private final String fileName;
    
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
    public List<Departamento> read() {
        List<Departamento> departamentos = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener el ID y el nombre del departamento
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    Departamento departamento = new Departamento(id, nombre);
                    departamentos.add(departamento);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return departamentos;
    }

    @Override
    public void update(Departamento departamentoToUpdate) {
        int idToUpdate = departamentoToUpdate.getId_departamento();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);

                if (id == idToUpdate) {
                    // Actualizar la línea con la información del departamento modificado
                    lines.add(idToUpdate + "," + departamentoToUpdate.getNombre_departamento());
                } else {
                    lines.add(line);
                }
            }

            // Actualizar la lista
            lista.remove(departamentoToUpdate);
            lista.add(departamentoToUpdate);

            // Escribir de nuevo el contenido modificado
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Departamento actualizado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

       public static Departamento buscarDepartamentoPorId(int idBuscado, String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener el ID y el nombre del departamento
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0].trim());
                    if (id == idBuscado) {
                        String nombre = partes[1].trim();
                        return new Departamento(id, nombre);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return null; // Retorna null si el departamento no se encuentra
    }
    
}
