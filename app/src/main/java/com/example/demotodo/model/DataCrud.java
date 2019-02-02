package com.example.demotodo.model;

public interface DataCrud {
    void get();
    void post(ItemVO task);
    void put(ItemVO item);
    void delete(ItemVO item);
}