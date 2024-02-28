/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.h2;

import java.sql.Connection;

/**
 *
 * @author danal
 */
public class EstudianteRepositoryH2 {
    private final Connection connection;

    public EstudianteRepositoryH2(Connection connection) {
        this.connection = connection;
    }
}
