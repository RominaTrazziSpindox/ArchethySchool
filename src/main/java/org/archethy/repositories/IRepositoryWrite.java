package org.archethy.repositories;

public interface IRepositoryWrite<T> {

    // Metodo per inserire un oggetto di tipo T
    boolean insert(T obj);

    // Metodo per aggiornare i dati di un oggetto di tipo T
    boolean update(T obj);

    // Metodo per eliminare un oggetto tramite id
    boolean delete(int id);
}
