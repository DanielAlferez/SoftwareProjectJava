/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.file;

import com.mycompany.poo.repositories.interfaces.IVisualizarInformacion;
import com.mycompany.poo.entities.Lugar;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Estudiante_MCA
 */
public class ListaLugares implements IVisualizarInformacion {
    private List<Lugar> lista;
    
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
    
    public void adicionar(Lugar lugar){
        this.lista.add(lugar);
    }
     
    public void remover(Lugar lugar){
        this.lista.remove(lugar);
    }
    
}
