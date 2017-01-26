package com.pluchyn.testproject.service.impl;

import com.pluchyn.testproject.service.Observable;
import com.pluchyn.testproject.service.Observer;

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

    public WebService(String host) {
        this.host = host;
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
    public void notifyObservers(boolean state) {
        String host = this.host;
        String port = this.port;

        if (state) {
            observerList.forEach(o -> o.notifyServiceWorking(host, port));
        } else {
            observerList.forEach(o -> o.notifyServiceNotWorking(host, port));
        }
    }

    @Override
    public void notifyObserver(Observer o, boolean state) {
        String host = this.host;
        String port = this.port;

        if (state) {
            o.notifyServiceWorking(host, port);
        } else {
            o.notifyServiceNotWorking(host, port);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WebService)) {
            return false;
        }
        if (this.port == null || ((WebService) obj).getPort() == null) {
            return this.host.equals(((WebService) obj).getHost());
        }
        return (this.host.equals(((WebService) obj).getHost()) && this.port.equals(((WebService) obj).getPort()));
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
