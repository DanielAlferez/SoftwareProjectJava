/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.entities;

import com.mycompany.poo.entities.Lugar;

/**
 *
 * @author Estudiante_MCA
 */
public class Programa {
    private int id_programa;
    private String nombre;
    private int semestre;
    private Lugar direccion;
    
    public Programa(int id_programa, String nombre, int semestre, Lugar direccion){
        this.id_programa = id_programa;
        this.nombre = nombre;
        this.semestre = semestre;
        this.direccion = direccion;
    }
    
    public int getId_programa() {
        return id_programa;
    }

    public void setId_programa(int id_programa) {
        this.id_programa = id_programa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSemestres() {
        return semestre;
    }

    public void setSemestres(int semestres) {
        this.semestre = semestres;
    }

    public Lugar getLugar() {
        return direccion;
    }

    public void setLugar(Lugar direccion) {
        this.direccion = direccion;
    }
}
