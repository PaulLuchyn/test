package com.pluchyn.testproject;

import com.pluchyn.testproject.service.impl.WebService;
import com.pluchyn.testproject.service.impl.WebServiceObserver;
import com.pluchyn.testproject.service.PingService;
import com.pluchyn.testproject.service.impl.PingServiceImpl;
import com.pluchyn.testproject.util.Registry;
import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    private final static PingService pingService = new PingServiceImpl();

    public static void main(String[] args) {
        // Preparing data
        Timer timer = new Timer();
        Registry registry = new Registry();

        WebServiceObserver firstObserver = new WebServiceObserver("Paul");
        WebServiceObserver secondObserver = new WebServiceObserver("Ihor");

        WebService localhost = new WebService("localhost", "8080");
        WebService google = new WebService("google.com", null);
        WebService stackoverflow = new WebService("stackoverflow.com", null);

        registry.addRegistryEntry(firstObserver, localhost, 10000L);
        registry.addRegistryEntry(firstObserver, google, 1000L);
        registry.addRegistryEntry(secondObserver, stackoverflow, 60000L);

        ((PingServiceImpl) pingService).setRegistry(registry);

        /*
         * Scheduling task to do lookup from registry.
         * Zero delay means that lookup is starting immediately after application starts.
         * Period "10000" means that lookup will be performed every tem seconds.
         */
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pingService.checkConnection(registry.lookup());
            }
        }, 0, 10000);
    }
}
