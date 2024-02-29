package com.mycompany.poo.factories;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.repositories.file.DepartamentosCreados;
import com.mycompany.poo.repositories.file.EstudiantesInscritos;
import com.mycompany.poo.repositories.file.ListaLugares;
import com.mycompany.poo.repositories.file.MunicipiosCreados;
import com.mycompany.poo.repositories.h2.EstudianteRepositoryH2;
import com.mycompany.poo.repositories.h2.LugarRepositoryH2;
import com.mycompany.poo.repositories.h2.MunicipioRepositoryH2;
import com.mycompany.poo.repositories.h2.ProgramaRepositoryH2;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.repositories.interfaces.IRepositoryUpdatable;

import java.sql.Connection;

public class FileFactory implements IRepositoryFactory{

    @Override
    public IRepository<Departamento> createDepartamentoRepository() {
        return new DepartamentosCreados("Departamentos.txt");
    }
    
     @Override
    public IRepository<Municipio> createMunicipioRepository() {
        return new MunicipiosCreados("Municipios.txt");
    }
    
    @Override
    public IRepository<Lugar> createLugarRepository() {
        return new ListaLugares("Lugares.txt");
    }
    
    @Override
    public IRepository<Estudiante> createEstudianteRepository() {
        return new EstudiantesInscritos("Estudiantes.txt");
    }

    @Override
    public Object createProgramaRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}