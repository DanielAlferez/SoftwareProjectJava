/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

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
public class ListadoObjetosInformacion {
    private List<IVisualizarInformacion> lista = new ArrayList<>();
     
    public void adicionar(IVisualizarInformacion visualizarInformacion){
        this.lista.add(visualizarInformacion);
    }
     
    public void remover(IVisualizarInformacion visualizarInformacion){
        this.lista.remove(visualizarInformacion);
    }
     
   public void cargar(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
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
