package com.mycompany.poo.factories;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.repositories.file.DepartamentosCreados;
import com.mycompany.poo.repositories.file.MunicipiosCreados;
import com.mycompany.poo.repositories.interfaces.IRepository;

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
}