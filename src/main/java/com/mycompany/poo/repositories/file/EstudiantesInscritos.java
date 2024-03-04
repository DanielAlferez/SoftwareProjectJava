/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;


import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.repositories.interfaces.IRepository;
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
public class EstudiantesInscritos implements IVisualizarInformacion, IRepository<Estudiante> {
    private List<Estudiante> listado = new ArrayList<>();
    private String fileName;
    
     public EstudiantesInscritos(String fileName) {
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
    public void create(Estudiante nuevoEstudiante) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            // Obtener información del estudiante, programa y dirección
            String nombre = nuevoEstudiante.getNombre();
            String apellido = nuevoEstudiante.getApellido();
            int idEstudiante = (int) nuevoEstudiante.getId();
            double codigo = nuevoEstudiante.getCodigo();

            Programa programa = nuevoEstudiante.getPrograma();
            int idPrograma = programa.getId_programa();
            String nombrePrograma = programa.getNombre();
            int semestrePrograma = programa.getSemestres();

            Lugar direccion = nuevoEstudiante.getDireccion();
            String direccionLugar = direccion.getDireccion();
            Departamento departamento = direccion.getDepartamento();
            int idDepartamento = departamento.getId_departamento();
            String nombreDepartamento = departamento.getNombre_departamento();
            Municipio municipio = direccion.getMunicipio();
            int idMunicipio = municipio.getId_municipio();
            String nombreMunicipio = municipio.getNombre_municipio();

            // Escribir la información en el archivo
            writer.println(nombre + "," + apellido + "," + idEstudiante + "," + codigo + "," +
                    idPrograma + "," + nombrePrograma + "," + semestrePrograma + "," +
                    direccionLugar + "," + idDepartamento + "," + nombreDepartamento + "," +
                    idMunicipio + "," + nombreMunicipio);

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void delete(Estudiante estudianteToRemove) {
        int idToRemove = (int) estudianteToRemove.getId();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is "nombre,apellido,idEstudiante,codigo,idPrograma,nombrePrograma,semestrePrograma,direccionLugar,idDepartamento,nombreDepartamento,idMunicipio,nombreMunicipio"
                String[] parts = line.split(",");
                int idEstudiante = Integer.parseInt(parts[2].trim());

                if (idEstudiante != idToRemove) {
                    lines.add(line);
                }
            }

            // Update the list
            listado.remove(estudianteToRemove);

            // Write back the modified content
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Estudiante eliminado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void read() {
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
    public void update(Estudiante entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
