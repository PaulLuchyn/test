package com.pluchyn.testproject;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;
import org.apache.log4j.Logger;

public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {

        WebServiceObserver serviceObserver = new WebServiceObserver("Paul");
        WebServiceObserver serviceObserver2 = new WebServiceObserver("Ihor");

        WebService service = new WebService("localhost","8080");

        service.addObserver(serviceObserver);
        service.addObserver(serviceObserver2);

        while(true){
            service.notifyObservers(service.checkState());
        }
    }
}
