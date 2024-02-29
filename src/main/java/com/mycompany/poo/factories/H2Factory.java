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
import com.mycompany.poo.repositories.file.DepartamentosCreados;
import com.mycompany.poo.repositories.h2.DepartamentoRepositoryH2;
import com.mycompany.poo.repositories.h2.EstudianteRepositoryH2;
import com.mycompany.poo.repositories.h2.LugarRepositoryH2;
import com.mycompany.poo.repositories.h2.MunicipioRepositoryH2;
import com.mycompany.poo.repositories.h2.ProgramaRepositoryH2;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IRepositoryUpdatable;
import java.sql.Connection;

/**
 *
 * @author danal
 */
public class H2Factory implements IRepositoryFactory{
    
    @Override
    public IRepositoryUpdatable<Departamento> createDepartamentoRepository() {
        return new DepartamentoRepositoryH2();
    }
    
    @Override
    public IRepositoryUpdatable<Municipio> createMunicipioRepository() {
        return new MunicipioRepositoryH2();
    }
    
    @Override
    public IRepositoryUpdatable<Lugar> createLugarRepository() {
        return new LugarRepositoryH2();
    }
    
    @Override
    public IRepositoryUpdatable<Programa> createProgramaRepository() {
        return new ProgramaRepositoryH2();
    }
    
    @Override
    public IRepositoryUpdatable<Estudiante> createEstudianteRepository() {
        return new EstudianteRepositoryH2();
    }
    
}
