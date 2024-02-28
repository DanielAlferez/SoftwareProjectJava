/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.interfaces;

/**
 *
 * @author danal
 */
public interface IRepository<T> {
    void create(T entity);
    void delete(T entity);
    void read();
}
