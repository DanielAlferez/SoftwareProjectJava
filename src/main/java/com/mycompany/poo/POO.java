package com.mycompany.poo;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.factories.FactoryDB;
import com.mycompany.poo.factories.FileFactory;
import com.mycompany.poo.factories.H2Factory;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.factories.IRepositoryFactory;
import java.io.IOException;
import java.sql.SQLException;

public class POO {

    public static void main(String[] args) throws SQLException, IOException {
           
        Departamento D1 =  new Departamento(1, "Meta");
        Departamento D2 =  new Departamento(2, "Cauca");
        Departamento D3 =  new Departamento(3, "Vichada");
        
        Municipio M1 = new Municipio(1, "Villavicencio", D1);
        Municipio M2 = new Municipio(2, "Popayan", D2);
        Municipio M3 = new Municipio(3, "Cumaribo", D3);
        
        Lugar L1 = new Lugar("dir 1", D1, M1);
        Lugar L2 = new Lugar("dir 2", D2, M2);
        Lugar L3 = new Lugar("dir 3", D1, M1);
        
        Programa P1 = new Programa(1, "Sistemas", 2, L1);
        Programa P2 = new Programa(2, "Electronica", 6, L2);
        Programa P3 = new Programa(3, "Biologia", 4, L3);
        
        Estudiante E1 = new Estudiante(1, "Andres", "Matoma", 4322, P3, L3);
        Estudiante E2 = new Estudiante(2, "Daniel", "Alferez", 4302, P1, L1);
        Estudiante E3 = new Estudiante(3, "Jose", "Perez", 4339, P3, L3);
        
        FactoryDB factory = FactoryDB.getInstance();
        IRepositoryFactory dbFactory = factory.getDatabase();;
        
        //Departamento
        IRepository<Departamento> departamentoRepository = dbFactory.createDepartamentoRepository();
        departamentoRepository.create(D1);
        departamentoRepository.create(D2);
        departamentoRepository.create(D3);
        Departamento D3_updated =  new Departamento(3, "Cundinamarca");
        departamentoRepository.update(D3_updated);
        departamentoRepository.delete(D2);
        departamentoRepository.read();
        
        //Municipio
        IRepository<Municipio> municipioRepository = dbFactory.createMunicipioRepository();
        municipioRepository.create(M1);
        municipioRepository.create(M2);
        municipioRepository.create(M3);
        Municipio M3_updated = new Municipio(3, "Cáqueza", D3);
        municipioRepository.update(M3_updated);
        municipioRepository.delete(M2);
        municipioRepository.read();
        
        //Lugar
        IRepository<Lugar> lugarRepository = dbFactory.createLugarRepository();
        lugarRepository.create(L1);
        lugarRepository.create(L2);
        lugarRepository.create(L3);
        Lugar L3_updated = new Lugar("dir 3.1", D1, M1);
        lugarRepository.update(L3_updated);
        lugarRepository.delete(L2);
        lugarRepository.read();
        
        //Programa
        IRepository<Programa> programaRepository = dbFactory.createProgramaRepository();
        programaRepository.create(P1);
        programaRepository.create(P2);
        programaRepository.create(P3);
        Programa P3_updated = new Programa(3, "Agroindustrial", 4, L3);
        programaRepository.update(P3_updated);
        programaRepository.delete(P2);
        programaRepository.read();
         
        //Estudiante
        IRepository<Estudiante> estudianteRepository = dbFactory.createEstudianteRepository();
        estudianteRepository.create(E1);
        estudianteRepository.create(E2);
        estudianteRepository.create(E3);
        Estudiante E3_updated = new Estudiante(3, "Jose", "Martinez", 4339, P3, L3);
        estudianteRepository.update(E3_updated);
        estudianteRepository.delete(E2);
        estudianteRepository.read();
    }
}
