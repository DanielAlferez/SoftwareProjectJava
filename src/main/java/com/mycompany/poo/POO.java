package com.mycompany.poo;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.factories.FileFactory;
import com.mycompany.poo.factories.H2Factory;
import com.mycompany.poo.repositories.interfaces.IRepository;
import com.mycompany.poo.factories.IRepositoryFactory;
import com.mycompany.poo.repositories.interfaces.IRepositoryUpdatable;
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
        
        IRepositoryFactory dbFactoryh2;
        dbFactoryh2 = new H2Factory();
        IRepositoryFactory fileFactory;
        fileFactory = new FileFactory();

        //Departamento
         
        IRepositoryUpdatable<Departamento> departamentoRepositoryh2 = (IRepositoryUpdatable<Departamento>) dbFactoryh2.createDepartamentoRepository();
        IRepository<Departamento> departamentoRepository = (IRepository<Departamento>) fileFactory.createDepartamentoRepository();

        departamentoRepositoryh2.create(D1);
        departamentoRepositoryh2.create(D2);
        departamentoRepositoryh2.create(D3);
        departamentoRepositoryh2.read();
        
        departamentoRepository.create(D1);
        departamentoRepository.create(D2);
        departamentoRepository.create(D3);
        departamentoRepository.read();
        
        
        //Municipio
        
        IRepositoryUpdatable<Municipio> municipioRepositoryh2 = (IRepositoryUpdatable<Municipio>) dbFactoryh2.createMunicipioRepository();
        IRepository<Municipio> municipioRepository = (IRepository<Municipio>) fileFactory.createMunicipioRepository();
        
        municipioRepositoryh2.create(M1);
        municipioRepositoryh2.create(M2);
        municipioRepositoryh2.create(M3);
        municipioRepositoryh2.read();
        
        municipioRepository.create(M1);
        municipioRepository.create(M2);
        municipioRepository.create(M3);
        municipioRepository.read();
        
        //Lugar
        
         IRepositoryUpdatable<Lugar> lugarRepositoryh2 = (IRepositoryUpdatable<Lugar>) dbFactoryh2.createLugarRepository();
         IRepository<Lugar> lugarRepository = (IRepository<Lugar>) fileFactory.createLugarRepository();
         lugarRepositoryh2.create(L1);
         lugarRepositoryh2.create(L2);
         lugarRepositoryh2.create(L3);
         lugarRepositoryh2.read();
         
         lugarRepository.create(L1);
         lugarRepository.create(L2);
         lugarRepository.create(L3);
         lugarRepository.read();
         
         //Programa
         
         IRepositoryUpdatable<Programa> programaRepositoryh2 = (IRepositoryUpdatable<Programa>) dbFactoryh2.createProgramaRepository();
         programaRepositoryh2.create(P1);
         programaRepositoryh2.create(P2);
         programaRepositoryh2.create(P3);
         programaRepositoryh2.read();
         
         //Estudiante
         
         IRepositoryUpdatable<Estudiante> estudianteRepositoryh2 = (IRepositoryUpdatable<Estudiante>) dbFactoryh2.createEstudianteRepository();
         IRepository<Estudiante> estudianteRepository = (IRepository<Estudiante>) fileFactory.createEstudianteRepository();
         
         estudianteRepositoryh2.create(E1);
         estudianteRepositoryh2.create(E2);
         estudianteRepositoryh2.create(E3);
         estudianteRepositoryh2.read();
         
         estudianteRepository.create(E1);
         estudianteRepository.create(E2);
         estudianteRepository.create(E3);
         estudianteRepository.read();
    }
}
