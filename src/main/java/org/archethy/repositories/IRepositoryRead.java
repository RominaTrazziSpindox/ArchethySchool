package org.archethy.repositories;

import java.util.List;

public interface IRepositoryRead<T> {

    // Metodo per leggere i dati di un oggetto di tipo T tramite un id
    T getById(int id);

    // Metodo per recuperare una lista di oggetti di tipo T
    List<T> getAll();
}