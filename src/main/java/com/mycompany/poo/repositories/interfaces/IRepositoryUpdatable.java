/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo.repositories.interfaces;

/**
 *
 * @author danal
 */
public interface IRepositoryUpdatable<T> extends IRepository<T>{
    void update(T entity);
}
