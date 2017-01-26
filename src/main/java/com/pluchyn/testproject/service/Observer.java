package com.pluchyn.testproject.service;

public interface Observer {

    void notifyServiceWorking(String host, String port);

    void notifyServiceNotWorking(String host, String port);

}
