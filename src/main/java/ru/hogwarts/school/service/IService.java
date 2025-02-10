package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface IService<T> {

    T add(T faculty);
    T upd(T faculty);
    T remove(long id);
    T find(long id);
    Collection<T> getAll();
}
