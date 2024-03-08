/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Programa;
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
public class ProgramasCreados implements IVisualizarInformacion, IRepository<Programa>{
    private final List<Programa> lista = new ArrayList<>();
    private final String fileName;
    
    public ProgramasCreados(String nombreArchivo) {
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
    public void create(Programa nuevoPrograma) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            lista.add(nuevoPrograma);  // Agregar el nuevoPrograma a la lista

            // Obtener información del programa y lugar
            int id = nuevoPrograma.getId_programa();
            String nombrePrograma = nuevoPrograma.getNombre();
            int semestre = nuevoPrograma.getSemestre();
            String direccion = nuevoPrograma.getLugar().getDireccion();

            // Escribir la información en el archivo
            writer.println(id + "," + nombrePrograma + "," + semestre + "," + direccion);

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

     
    @Override
    public void delete(Programa programaToRemove) {
       int idToRemove = programaToRemove.getId_programa();
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
            lista.remove(programaToRemove);

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
    public List<Programa> read() {
        List<Programa> programas = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener los atributos del programa
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    int idPrograma = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    int semestre = Integer.parseInt(partes[2].trim());
                    String direccion = partes[3].trim();

                    // Obtener el objeto Lugar asociado al Programa
                    Lugar lugar = ListaLugares.buscarLugarPorDireccion(direccion, "Lugares.txt");

                    // Construir el objeto Programa y agregarlo a la lista
                    Programa programa = new Programa(idPrograma, nombre, semestre, lugar);
                    programas.add(programa);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return programas;
    }

    @Override
    public void update(Programa programaToUpdate) {
        int idToUpdate = programaToUpdate.getId_programa();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);

                if (id == idToUpdate) {
                    // Actualizar la línea con la información del programa modificado
                    lines.add(idToUpdate + "," + programaToUpdate.getNombre() + "," +
                              programaToUpdate.getSemestre() + "," + programaToUpdate.getLugar().getDireccion());
                } else {
                    lines.add(line);
                }
            }

            // Actualizar la lista
            lista.remove(programaToUpdate);
            lista.add(programaToUpdate);

            // Escribir de nuevo el contenido modificado
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Programa actualizado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

    public static Programa buscarProgramaPorId(int idBuscado, String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener los atributos del programa
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    int idPrograma = Integer.parseInt(partes[0].trim());
                    if (idPrograma == idBuscado) {
                        String nombre = partes[1].trim();
                        int semestre = Integer.parseInt(partes[2].trim());
                        String direccion = partes[3].trim();

                        // Obtener el objeto Lugar asociado al Programa
                        Lugar lugar = ListaLugares.buscarLugarPorDireccion(direccion,fileName);

                        // Construir y retornar el objeto Programa
                        return new Programa(idPrograma, nombre, semestre, lugar);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return null; // Retorna null si el programa no se encuentra
    }
}
