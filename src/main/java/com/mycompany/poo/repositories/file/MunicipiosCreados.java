/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Municipio;
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
public class MunicipiosCreados implements IVisualizarInformacion, IRepository<Municipio>{
    private List<Municipio> lista = new ArrayList<>();
    private String fileName;
    
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
    
    public void create(Municipio nuevoMunicipio){
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            lista.add(nuevoMunicipio);  // Agregar el nuevoMunicipio a la lista

            for (Municipio municipio : lista) {
                // Obtener información del municipio y departamento
                int idMunicipio = municipio.getId_municipio();
                String nombreMunicipio = municipio.getNombre_municipio();
                int idDepartamento = municipio.getDepartamento().getId_departamento();
                String nombreDepartamento = municipio.getDepartamento().getNombre_departamento();

                // Escribir la información en el archivo
                writer.println(idMunicipio + "," + nombreMunicipio + "," + idDepartamento + "," + nombreDepartamento);
            }

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

     
    public void delete(Municipio municipio){
        this.lista.remove(municipio);
    }
     
   
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
}
