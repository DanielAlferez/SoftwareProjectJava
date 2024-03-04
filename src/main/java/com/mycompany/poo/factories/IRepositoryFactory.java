/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.factories;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.repositories.interfaces.IRepository;

/**
 *
 * @author danal
 */

public interface IRepositoryFactory{
    IRepository<Departamento> createDepartamentoRepository();
    IRepository<Municipio>createMunicipioRepository();
    IRepository<Lugar> createLugarRepository();
    IRepository<Programa> createProgramaRepository();
    IRepository<Estudiante> createEstudianteRepository();
}