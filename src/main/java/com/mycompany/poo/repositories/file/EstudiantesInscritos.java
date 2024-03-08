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
    private final List<Estudiante> listado = new ArrayList<>();
    private final String fileName;
    
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
            int semestrePrograma = programa.getSemestre();

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
    public List<Estudiante> read() {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener los atributos del estudiante
                String[] partes = linea.split(",");
                if (partes.length == 12) {
                    String nombre = partes[0].trim();
                    String apellido = partes[1].trim();
                    int idEstudiante = Integer.parseInt(partes[2].trim());

                    double codigo = Double.parseDouble(partes[3].trim());
                    int idPrograma = Integer.parseInt(partes[4].trim());
                    String idDireccion = partes[7].trim();

                    // Obtener el objeto Programa asociado al Estudiante
                    Programa programa = ProgramasCreados.buscarProgramaPorId(idPrograma,"Programas.txt");

                    // Obtener el objeto Lugar asociado al Estudiante
                    Lugar direccion = ListaLugares.buscarLugarPorDireccion(idDireccion,"Lugares.txt");

                    // Construir el objeto Estudiante y agregarlo a la lista
                    Estudiante estudiante = new Estudiante(idEstudiante, nombre, apellido, codigo, programa, direccion);
                    estudiantes.add(estudiante);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return estudiantes;
    }

    @Override
    public void update(Estudiante estudianteToUpdate) {
        int idToUpdate = (int) estudianteToUpdate.getId();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is "nombre,apellido,idEstudiante,codigo,idPrograma,nombrePrograma,semestrePrograma,direccionLugar,idDepartamento,nombreDepartamento,idMunicipio,nombreMunicipio"
                String[] parts = line.split(",");
                int idEstudiante = Integer.parseInt(parts[2].trim());

                if (idEstudiante == idToUpdate) {
                    // Actualizar la línea con la información del estudiante modificado
                    Programa programa = estudianteToUpdate.getPrograma();
                    Lugar direccion = estudianteToUpdate.getDireccion();

                    lines.add(estudianteToUpdate.getNombre() + "," + estudianteToUpdate.getApellido() + "," +
                              estudianteToUpdate.getId() + "," + estudianteToUpdate.getCodigo() + "," +
                              programa.getId_programa() + "," + programa.getNombre() + "," +
                              programa.getSemestre() + "," +
                              direccion.getDireccion() + "," + direccion.getDepartamento().getId_departamento() + "," +
                              direccion.getDepartamento().getNombre_departamento() + "," +
                              direccion.getMunicipio().getId_municipio() + "," +
                              direccion.getMunicipio().getNombre_municipio());
                } else {
                    lines.add(line);
                }
            }

            // Actualizar la lista
            listado.remove(estudianteToUpdate);
            listado.add(estudianteToUpdate);

            // Escribir de nuevo el contenido modificado
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Estudiante actualizado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }


}
