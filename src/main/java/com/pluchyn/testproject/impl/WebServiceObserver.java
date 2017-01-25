package com.pluchyn.testproject.impl;

import com.pluchyn.testproject.App;
import com.pluchyn.testproject.interfaces.Observer;
import org.apache.log4j.Logger;

/**
 * This class represents concrete observer (user)
 */
public class WebServiceObserver implements Observer {

    private final static Logger logger = Logger.getLogger(App.class);

    //This field is used for identifying observers
    private String name;

    public WebServiceObserver() {
    }

    public WebServiceObserver(String name) {
        this.name = name;
    }

    @Override
    public void notifyServiceWorking(String host, String port) {
        //Here must be some important logic which will change observer's state
        logger.info("Report from " + name + ":Service " + host + " on port: " + port + " is working");
    }

    @Override
    public void notifyServiceNotWorking(String host, String port) {
        //Here must be some important logic which will change observer's state
        logger.info("Report from " + name + ":Service " + host + " on port: " + port + " is not working");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WebServiceObserver)) {
            return false;
        }
        return this.name.equals(((WebServiceObserver) obj).name);
    }
}
