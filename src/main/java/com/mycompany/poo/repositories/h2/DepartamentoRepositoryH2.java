package com.mycompany.poo.repositories.h2;

import com.mycompany.poo.entities.Departamento;
import com.mycompany.poo.factories.ConnectionDatabase;
import com.mycompany.poo.repositories.interfaces.IRepositoryUpdatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartamentoRepositoryH2 implements IRepositoryUpdatable<Departamento> {


    public DepartamentoRepositoryH2() {
    }

    @Override
    public void create(Departamento departamento) {
        System.out.println("adentro piro");
    }

    @Override
    public void read() {
      
    }

    @Override
    public void delete(Departamento departamento) {
       
    }

    @Override
    public void update(Departamento departamento) {
       
    }
}
