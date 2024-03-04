/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.factories;
/**
 *
 * @author danal
 * @param <T>
 */

public interface IRepositoryFactory<T>{
    T createDepartamentoRepository();
    T createMunicipioRepository();
    T createLugarRepository();
    T createProgramaRepository();
    T createEstudianteRepository();
}