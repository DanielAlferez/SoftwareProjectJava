/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.entities;

/**
 *
 * @author Estudiante_MCA
 */
public class Lugar {
    private String direccion;
    private Departamento departamento;
    private Municipio municipio;
    
    public Lugar(String direccion, Departamento departamento, Municipio municipio) {
        this.direccion = direccion;
        this.departamento = departamento;
        this.municipio = municipio;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
