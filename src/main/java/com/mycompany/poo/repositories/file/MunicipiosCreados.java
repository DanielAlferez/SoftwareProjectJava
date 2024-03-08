/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Municipio;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Estudiante_MCA
 */
public class MunicipiosCreados implements IVisualizarInformacion, IRepository<Municipio>{
    private final List<Municipio> lista = new ArrayList<>();
    private final String fileName;
    
    public MunicipiosCreados(String nombreArchivo) {
        this.fileName = nombreArchivo;
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
    public void create(Municipio nuevoMunicipio){
         try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            lista.add(nuevoMunicipio);  // Agregar el nuevoMunicipio a la lista

            // Obtener información del municipio y departamento
            int idMunicipio = nuevoMunicipio.getId_municipio();
            String nombreMunicipio = nuevoMunicipio.getNombre_municipio();
            int idDepartamento = nuevoMunicipio.getDepartamento().getId_departamento();
            String nombreDepartamento = nuevoMunicipio.getDepartamento().getNombre_departamento();

            // Escribir la información en el archivo
            writer.println(idMunicipio + "," + nombreMunicipio + "," + idDepartamento + "," + nombreDepartamento);

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

     
    @Override
    public void delete(Municipio municipioToRemove) {
        int idToRemove = municipioToRemove.getId_municipio();
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
            lista.remove(municipioToRemove);

            // Write back the modified content
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Municipio eliminado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

     
   
    @Override
    public List<Municipio> read() {
        List<Municipio> municipios = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener el ID, nombre y ID del departamento del municipio
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    int idMunicipio = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    int departamentoId = Integer.parseInt(partes[2].trim());

                    // Obtener el objeto Departamento asociado al Municipio
                    Departamento departamento = DepartamentosCreados.buscarDepartamentoPorId(departamentoId, "Departamentos.txt");

                    // Construir el objeto Municipio y agregarlo a la lista
                    Municipio municipio = new Municipio(idMunicipio, nombre, departamento);
                    municipios.add(municipio);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return municipios;
    } 

    @Override
    public void update(Municipio municipioToUpdate) {
        int idToUpdate = municipioToUpdate.getId_municipio();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());

                if (id == idToUpdate) {
                    // Actualizar la línea con la información del municipio modificado
                    lines.add(municipioToUpdate.getId_municipio() + "," +
                              municipioToUpdate.getNombre_municipio() + "," +
                              municipioToUpdate.getDepartamento().getId_departamento() + "," +
                              municipioToUpdate.getDepartamento().getNombre_departamento());
                } else {
                    lines.add(line);
                }
            }

            // Actualizar la lista
            lista.remove(municipioToUpdate);
            lista.add(municipioToUpdate);

            // Escribir de nuevo el contenido modificado
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Municipio actualizado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }
    
    public static Municipio buscarMunicipioPorId(int idBuscado, String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener el ID, nombre y ID del departamento del municipio
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    int id = Integer.parseInt(partes[0].trim());
                    if (id == idBuscado) {
                        String nombre = partes[1].trim();
                        int idDepartamento = Integer.parseInt(partes[2].trim());
                        // Obtener el objeto Departamento asociado al municipio
                        Departamento departamento = DepartamentosCreados.buscarDepartamentoPorId(idDepartamento, "Departamentos.txt");
                        if (departamento != null) {
                            return new Municipio(id, nombre, departamento);
                        } else {
                            System.err.println("No se pudo encontrar el departamento asociado al municipio con ID: " + id);
                            return null;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return null; // Retorna null si el municipio no se encuentra
    }

}
