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
import java.io.File;
import java.io.FileNotFoundException;
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
public class EstudiantesInscritos implements IVisualizarInformacion {
    private List<Estudiante> listado = new ArrayList<>();
    
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
    
    public void adicionar(Estudiante estudiante){
        this.listado.add(estudiante);
    }
     
    public void remover(Estudiante estudiante){
        this.listado.remove(estudiante);
    }

    public void cargar(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Estudiante estudiante : listado) {
                // Obtener información del estudiante, programa y dirección
                String nombre = estudiante.getNombre();
                String apellido = estudiante.getApellido();
                int idEstudiante = (int) estudiante.getId();
                double codigo = estudiante.getCodigo();
                
                Programa programa = estudiante.getPrograma();
                int idPrograma = programa.getId_programa();
                String nombrePrograma = programa.getNombre();
                int semestrePrograma = programa.getSemestres();
                
                Lugar direccion = estudiante.getDireccion();
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
            }

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    
    public void leer(String nombreArchivo) {
        try (Scanner scanner = new Scanner(new File(nombreArchivo))) {
            System.out.println("Contenido del archivo " + nombreArchivo + ":\n");
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
            }
            System.out.println("\nFin del archivo.\n");
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }
}
