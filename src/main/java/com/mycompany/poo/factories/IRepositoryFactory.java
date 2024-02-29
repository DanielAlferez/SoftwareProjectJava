/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.factories;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.repositories.file.DepartamentosCreados;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IRepositoryUpdatable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author danal
 */
public interface IRepositoryFactory<T>{
    T createDepartamentoRepository();
    T createMunicipioRepository();
    T createLugarRepository();
    T createProgramaRepository();
    T createEstudianteRepository();
}