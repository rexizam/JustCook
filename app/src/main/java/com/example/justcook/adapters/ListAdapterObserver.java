package com.example.justcook.adapters;

public interface ListAdapterObserver {
    void registerObserver(Observer listAdapterObserver);
    void removeObserver(Observer listAdapterObserver);
    void notifyObservers();
}
