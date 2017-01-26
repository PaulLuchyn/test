package com.pluchyn.testproject.service;

public interface Observable {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(boolean state);

    void notifyObserver(Observer o, boolean state);
}
