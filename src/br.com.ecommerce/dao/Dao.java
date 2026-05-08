package br.com.ecommerce.dao;

import java.util.List;

public interface Dao<T> {
    void create(T entity);
    T read(int id);
    List<T> readAll();
    void update(T entity);
    void delete(int id);
}