package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface IService<T> {

    T add(T object);
    T upd(T object);
    void remove(Long id);
    T find(Long id);
    Collection<T> getAll();
}
