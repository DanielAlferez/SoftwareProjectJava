package com.mycompany.poo;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.entities.Estudiante;
import com.mycompany.poo.entities.Lugar;
import com.mycompany.poo.entities.Municipio;
import com.mycompany.poo.entities.Programa;
import com.mycompany.poo.factories.FileFactory;
import com.mycompany.poo.factories.IRepositoryFactory;
import com.mycompany.poo.repositories.interfaces.IRepository;

public class POO {

    public static void main(String[] args) {
        // Prueba de Repositorios
        IRepositoryFactory fileFactory = new FileFactory();

        // Repositorios para Estudiante, Lugar, Departamento y Municipio
        IRepository<Estudiante> estudianteRepository = (IRepository<Estudiante>) fileFactory.createEstudianteRepository();
        IRepository<Lugar> lugarRepository = (IRepository<Lugar>) fileFactory.createLugarRepository();
        IRepository<Departamento> departamentoRepository = (IRepository<Departamento>) fileFactory.createDepartamentoRepository();
        IRepository<Municipio> municipioRepository = (IRepository<Municipio>) fileFactory.createMunicipioRepository();

        // Crear objetos para probar
        Departamento meta = new Departamento(1, "Meta");
        Municipio villavo = new Municipio(1, "Villavicencio", meta);
        Lugar direccionEstudiante = new Lugar("Calle 123", meta, villavo);
        Lugar direccionUniversidad = new Lugar("Calle 321", meta, villavo);
        Programa programa = new Programa(1, "Sistemas", 9, direccionUniversidad);
        Estudiante estudiante = new Estudiante("Juan", "Perez", 123, 12345, programa, direccionEstudiante);

        // Crear y probar repositorios
        departamentoRepository.create(meta);
        municipioRepository.create(villavo);
        lugarRepository.create(direccionEstudiante);
        estudianteRepository.create(estudiante);

        // Leer y mostrar información
        System.out.println("Contenido de DepartamentoRepository:");
        departamentoRepository.read();
        System.out.println("Contenido de MunicipioRepository:");
        municipioRepository.read();
        System.out.println("Contenido de LugarRepository:");
        lugarRepository.read();
        System.out.println("Contenido de EstudianteRepository:");
        estudianteRepository.read();

        // Eliminar objetos y probar de nuevo
        estudianteRepository.delete(estudiante);

        System.out.println("Contenido de EstudianteRepository después de eliminar:");
        estudianteRepository.read();
    }
}
