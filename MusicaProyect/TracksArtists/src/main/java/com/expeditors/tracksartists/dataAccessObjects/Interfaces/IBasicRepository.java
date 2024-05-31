package com.expeditors.tracksartists.dataAccessObjects.Interfaces;

import com.expeditors.tracksartists.models.Artist;

public interface IBasicRepository<T> {
    public T add(T object);
    public boolean update(T object);
    public boolean delete(int id);
    public T getById(int id);
}
