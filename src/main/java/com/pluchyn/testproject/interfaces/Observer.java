package com.pluchyn.testproject.interfaces;

public interface Observer {

    void notifyServiceWorking(String host, String port);

    void notifyServiceNotWorking(String host, String port);

}
