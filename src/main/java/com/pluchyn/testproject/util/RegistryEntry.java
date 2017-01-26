package com.pluchyn.testproject.util;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;

import java.util.Date;

public class RegistryEntry {

    private WebServiceObserver user;

    private WebService service;

    private Long period;

    private Date nextRunAt;

    public void updateNextRunInfo() {
        nextRunAt = new Date(new Date().getTime() + period);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (!(obj instanceof RegistryEntry)) {
            return false;
        }
        return this.user.equals(((RegistryEntry) obj).user) && this.service.equals(((RegistryEntry) obj).service);
    }

    //Constructors

    public RegistryEntry(){
    }

    public RegistryEntry(WebService service, WebServiceObserver observer){
        this.service = service;
        this.user = observer;
    }

    public RegistryEntry(WebService service, WebServiceObserver observer, Long period){
        this.service = service;
        this.user = observer;
        this.period = period;
    }

    //Getters and setters

    public WebServiceObserver getUser() {
        return user;
    }

    public void setUser(WebServiceObserver user) {
        this.user = user;
    }

    public WebService getService() {
        return service;
    }

    public void setService(WebService service) {
        this.service = service;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Date getNextRunAt() {
        return nextRunAt;
    }

    public void setNextRunAt(Date nextRunAt) {
        this.nextRunAt = nextRunAt;
    }
}
