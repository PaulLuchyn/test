package com.pluchyn.testproject.interfaces;

public interface Observable {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    boolean checkState();

    void notifyObservers(boolean state);
}
