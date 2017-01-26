package com.pluchyn.testproject.service;

import com.pluchyn.testproject.service.impl.WebService;
import com.pluchyn.testproject.service.impl.WebServiceObserver;
import com.pluchyn.testproject.util.RegistryEntry;

import java.util.List;

public interface PingService {

    boolean connectAndNotify(WebService service, WebServiceObserver user, Long interval);

    void checkConnection(List<RegistryEntry> entries);
}