package com.pluchyn.testproject.service;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;
import com.pluchyn.testproject.interfaces.PingService;
import com.pluchyn.testproject.util.Registry;
import com.pluchyn.testproject.util.RegistryEntry;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PingServiceImpl implements PingService {

    private Registry registry = new Registry();

    private ThreadPoolExecutor wrapper = new ThreadPoolExecutor(2, 50, 600L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(512));

    public boolean connect(WebService service, WebServiceObserver user, Long interval) {
        if (!registry.isEntryExisting(service, user)) {
            registry.addRegistryEntry(new RegistryEntry(service, user, interval));
        }
        String host = service.getHost();
        String port = service.getPort();
        String adress = "http://" + host + ":" + port + "/";
        HttpURLConnection con = null;
        int responseCode;

        try {
            URL url = new URL(adress);
            con = (HttpURLConnection) url.openConnection();
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            //No connection to service, or connection can not be established
            return false;
        } finally {
            rewriteEntry(service, user);
        }

        //If connection is successful - service is working, otherwise - not working
        return responseCode == 200;
    }

    private void rewriteEntry(WebService service, WebServiceObserver observer) {
        registry.findEntryByServiceAndObserver(service, observer).updateLastAndNextRunInfo();
    }

}
