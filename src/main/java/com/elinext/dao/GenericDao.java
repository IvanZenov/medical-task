package com.elinext.dao;

import java.util.List;

public interface GenericDao<T> {
    T create(T entity);
    T findById(Long id);
    List<T> findAll();
}
