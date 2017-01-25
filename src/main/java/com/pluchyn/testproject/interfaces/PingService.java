package com.pluchyn.testproject.interfaces;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;

public interface PingService {

    boolean connect(WebService service, WebServiceObserver user, Long interval);

}
