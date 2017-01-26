package com.pluchyn.testproject.interfaces;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;
import com.pluchyn.testproject.util.RegistryEntry;

import java.util.List;

public interface PingService {

    boolean connectAndNotify(WebService service, WebServiceObserver user, Long interval);

    void checkConnection(List<RegistryEntry> entries);
}