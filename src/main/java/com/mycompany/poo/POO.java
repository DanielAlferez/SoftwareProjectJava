package com.mycompany.poo;

import com.mycompany.poo.factories.ConnectionDatabase;
import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.factories.FileFactory;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.factories.IRepositoryFactory;

public class POO {

    public static void main(String[] args) {
        // Prueba de MunicipiosCreados
        IRepositoryFactory dbFactory;
        dbFactory = new FileFactory();
        
        IRepository<Departamento> departamentoRepository = dbFactory.createDepartamentoRepository();
        IRepository<Municipio> municipioRepository = dbFactory.createMunicipioRepository();
        
        Departamento meta =  new Departamento(1, "Meta");
        Municipio villavo = new Municipio(1, "Villavicencio", meta);
        departamentoRepository.create(meta);
        municipioRepository.create(villavo);
    }
   
}
