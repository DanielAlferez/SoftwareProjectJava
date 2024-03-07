/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import static com.mycompany.poo.repositories.file.DepartamentosCreados.buscarDepartamentoPorId;
import com.mycompany.poo.repositories.interfaces.IRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Estudiante_MCA
 */
public class ListaLugares implements IVisualizarInformacion, IRepository<Lugar> {
    private final List<Lugar> lista = new ArrayList<>();
    private final String fileName;
    
     public ListaLugares(String fileName) {
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
    public void create(Lugar nuevoLugar) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            // Obtener información del nuevo lugar
            String direccion = nuevoLugar.getDireccion();
            int idDepartamento = nuevoLugar.getDepartamento().getId_departamento();
            String nombreDepartamento = nuevoLugar.getDepartamento().getNombre_departamento();
            int idMunicipio = nuevoLugar.getMunicipio().getId_municipio();
            String nombreMunicipio = nuevoLugar.getMunicipio().getNombre_municipio();

            // Escribir la información en el archivo
            writer.println(direccion + "," + idDepartamento + "," + nombreDepartamento + "," + idMunicipio + "," + nombreMunicipio);

            System.out.println("Guardado exitoso.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void delete(Lugar lugarToRemove) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is "direccion,idDepartamento,nombreDepartamento,idMunicipio,nombreMunicipio"
                String[] parts = line.split(",");
                String direccion = parts[0].trim();

                if (!direccion.equals(lugarToRemove.getDireccion())) {
                    lines.add(line);
                }
            }

            // Update the list
            lista.remove(lugarToRemove);

            // Write back the modified content
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Lugar eliminado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

    @Override
    public List<Lugar> read() {
        List<Lugar> lugares = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener la dirección, el ID del departamento y el ID del municipio
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String direccion = partes[0].trim();
                    int departamentoId = Integer.parseInt(partes[1].trim());
                    int municipioId = Integer.parseInt(partes[2].trim());

                    // Obtener el objeto Departamento y Municipio asociados al Lugar
                    Departamento departamento = DepartamentosCreados.buscarDepartamentoPorId(departamentoId,fileName);
                    Municipio municipio = MunicipiosCreados.buscarMunicipioPorId(municipioId, fileName);

                    // Construir el objeto Lugar y agregarlo a la lista
                    Lugar lugar = new Lugar(direccion, departamento, municipio);
                    lugares.add(lugar);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return lugares;
    }

    @Override
    public void update(Lugar lugarToUpdate) {
        String direccionToUpdate = lugarToUpdate.getDireccion();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is "direccion,idDepartamento,nombreDepartamento,idMunicipio,nombreMunicipio"
                String[] parts = line.split(",");
                String direccion = parts[0].trim();

                if (direccion.equals(direccionToUpdate)) {
                    // Actualizar la línea con la información del lugar modificado
                    lines.add(lugarToUpdate.getDireccion() + "," +
                              lugarToUpdate.getDepartamento().getId_departamento() + "," +
                              lugarToUpdate.getDepartamento().getNombre_departamento() + "," +
                              lugarToUpdate.getMunicipio().getId_municipio() + "," +
                              lugarToUpdate.getMunicipio().getNombre_municipio());
                } else {
                    lines.add(line);
                }
            }

            // Actualizar la lista
            lista.remove(lugarToUpdate);
            lista.add(lugarToUpdate);

            // Escribir de nuevo el contenido modificado
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (String updatedLine : lines) {
                    writer.println(updatedLine);
                }
            }

            System.out.println("Lugar actualizado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

    public static Lugar buscarPorDireccion(String direccionBuscada, String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                // Dividir la línea en partes para obtener la dirección, ID del departamento y ID del municipio del lugar
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String direccion = partes[0].trim();
                    if (direccion.equals(direccionBuscada)) {
                        int idDepartamento = Integer.parseInt(partes[1].trim());
                        int idMunicipio = Integer.parseInt(partes[2].trim());
                        
                        // Obtener el objeto Departamento y Municipio asociados al Lugar
                        Departamento departamento = buscarDepartamentoPorId(idDepartamento, "departamentos.txt");
                        Municipio municipio = MunicipiosCreados.buscarMunicipioPorId(idMunicipio, "municipios.txt");
                        
                        if (departamento != null && municipio != null) {
                            return new Lugar(direccion, departamento, municipio);
                        } else {
                            System.err.println("No se pudo encontrar el departamento o el municipio asociado al lugar con dirección: " + direccion);
                            return null;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        return null; // Retorna null si el lugar no se encuentra
    }
}
