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
    private String nombre;
    private String apellido;
    private int id_estudiante;
    private double codigo;
    private Programa programa;
    private Lugar direccion;
    
    public Estudiante(String nombre, String apellido, int id_estudiante, double codigo, Programa programa, Lugar direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_estudiante = id_estudiante;
        this.codigo = codigo;
        this.programa = programa;
        this.direccion = direccion;
        this.programa = programa;
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
