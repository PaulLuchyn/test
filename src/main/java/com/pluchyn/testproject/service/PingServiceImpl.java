package com.pluchyn.testproject.service;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;
import com.pluchyn.testproject.interfaces.PingService;
import com.pluchyn.testproject.util.Registry;
import com.pluchyn.testproject.util.RegistryEntry;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PingServiceImpl implements PingService {

    private Registry registry = new Registry();

    private ThreadPoolExecutor wrapper = new ThreadPoolExecutor(2, 50, 1000L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(50));

    private final static Logger logger = Logger.getLogger(PingServiceImpl.class);

    public PingServiceImpl() {
        wrapper.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private boolean connect(WebService service, WebServiceObserver user, Long interval) {
        if (!registry.exists(service, user)) {
            registry.addRegistryEntry(new RegistryEntry(service, user, interval));
        }
        String host = service.getHost();
        String port = service.getPort();

        String adress;
        if (port == null || "".equals(port)) {
            adress = "http://" + host + "/";
        } else {
            adress = "http://" + host + ":" + port + "/";
        }

        HttpURLConnection con = null;
        int responseCode;

        try {
            URL url = new URL(adress);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            // No connection to service, or connection can not be established
            return false;
        } finally {
            // Disconnecting and cleaning resources
            con.disconnect();
            // Updating "Next run" info
            updateNextRunInfo(service, user);
        }

        // If connection is successful - service is working, otherwise - not working
        return responseCode == 200;
    }

    @Override
    public boolean connectAndNotify(WebService service, WebServiceObserver user, Long interval) {
        boolean status = connect(service, user, interval);
        service.notifyObserver(user, status);
        return status;
    }

    @Override
    public void checkConnection(List<RegistryEntry> entries) {
        for (RegistryEntry currentEntry : entries) {
            wrapper.submit(
                    (Runnable) () -> connectAndNotify(currentEntry.getService(), currentEntry.getUser(),
                            currentEntry.getPeriod()));
        }
        logger.info("");
    }

    private void updateNextRunInfo(WebService service, WebServiceObserver observer) {
        registry.findEntryByServiceAndObserver(service, observer).updateNextRunInfo();
    }

    // Getters and setters

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

}
