/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.entities;

/**
 *
 * @author Estudiante_MCA
 */
public class Municipio {
    private int id_municipio;
    private String nombre_municipio;
    private Departamento departamento;
    
    public Municipio(int id_municipio, String nombre_municipio, Departamento departamento) {
        this.id_municipio = id_municipio;
        this.nombre_municipio = nombre_municipio;
        this.departamento = departamento;
    }
    
    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getNombre_municipio() {
        return nombre_municipio;
    }

    public void setNombre_municipio(String nombre_municipio) {
        this.nombre_municipio = nombre_municipio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
