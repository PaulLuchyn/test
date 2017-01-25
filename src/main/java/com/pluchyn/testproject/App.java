package com.pluchyn.testproject;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;
import com.pluchyn.testproject.interfaces.PingService;
import com.pluchyn.testproject.service.PingServiceImpl;
import org.apache.log4j.Logger;

public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    private final static PingService pingService = new PingServiceImpl();

    public static void main(String[] args) {

        WebServiceObserver serviceObserver = new WebServiceObserver("Paul");
        WebServiceObserver serviceObserver2 = new WebServiceObserver("Ihor");

        WebService service = new WebService("localhost","8080");

        service.addObserver(serviceObserver);

        while(true){
            service.notifyObserver(serviceObserver,pingService.connect(service, serviceObserver, 60000L));
        }
    }
}
