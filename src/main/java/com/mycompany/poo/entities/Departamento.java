/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.entities;

/**
 *
 * @author Estudiante_MCA
 */
public class Departamento {
    private int id_departamento;
    private String nombre_departamento;
    
    public Departamento(int id_departamento, String nombre_departamento) {
        this.id_departamento = id_departamento;
        this.nombre_departamento = nombre_departamento;
    }
    
    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }
}


