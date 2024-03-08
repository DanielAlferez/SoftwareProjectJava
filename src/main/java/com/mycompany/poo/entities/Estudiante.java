/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.entities;

/**
 *
 * @author Estudiante_MCA
 */
public class Estudiante {
    private int id_estudiante;
    private String nombre;
    private String apellido;
    private double codigo;
    private Programa programa;
    private Lugar direccion;
    
    public Estudiante(int id_estudiante, String nombre, String apellido, double codigo, Programa programa, Lugar direccion) {
        this.id_estudiante = id_estudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo = codigo;
        this.programa = programa;
        this.direccion = direccion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombres(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id_estudiante;
    }

    public void setId(int id) {
        this.id_estudiante = id;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public Lugar getDireccion() {
        return direccion;
    }

    public void setDireccion(Lugar direccion) {
        this.direccion = direccion;
    }
    
}
