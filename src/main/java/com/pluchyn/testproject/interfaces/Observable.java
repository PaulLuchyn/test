package com.pluchyn.testproject.interfaces;

public interface Observable {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(boolean state);

    void notifyObserver(Observer o, boolean state);
}
