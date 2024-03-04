/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Estudiante_MCA
 */
public class ListadoObjetosInformacion implements IRepository<IVisualizarInformacion>  {
    private List<IVisualizarInformacion> lista = new ArrayList<>();
    private String fileName;
    
     public ListadoObjetosInformacion(String fileName) {
        this.fileName = fileName;
    }
         
    @Override
    public void create(IVisualizarInformacion entity) {
         try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (IVisualizarInformacion objeto : lista) {
                // Escribir la información del objeto en el archivo
                writer.println("Nombre de la Clase: " + objeto.nombreClase());
                writer.println("Información del Objeto: " + objeto.informacionObjeto());
                writer.println("---------------------");
            }

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void delete(IVisualizarInformacion entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public void update(IVisualizarInformacion entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}