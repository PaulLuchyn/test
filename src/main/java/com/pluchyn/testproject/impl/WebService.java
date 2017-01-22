package com.pluchyn.testproject.impl;

import com.pluchyn.testproject.interfaces.Observable;
import com.pluchyn.testproject.interfaces.Observer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents concrete observable web-service.
 */
public class WebService implements Observable {

    private List<Observer> observerList = new ArrayList<>();

    private String host;

    private String port;

    //Constructors

    public WebService() {
    }

    public WebService(String host, String port) {
        this.host = host;
        this.port = port;
    }

    //Important methods

    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public boolean checkState() {
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
        }

        //If connection is successful - service is working, otherwise - not working
        return responseCode == 200;
    }

    @Override
    public void notifyObservers(boolean state) {
        String host = this.host;
        String port = this.port;

        if (state) {
            observerList.forEach(o -> o.notifyServiceWorking(host, port));
        } else {
            observerList.forEach(o -> o.notifyServiceNotWorking(host, port));
        }
    }

    //Getters and setters

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
