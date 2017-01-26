package com.pluchyn.testproject.util;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Registry {

    private ArrayList<RegistryEntry> registryEntries = new ArrayList<>();

    public void addRegistryEntry(WebServiceObserver observer, WebService service, Long period) {
        registryEntries.add(new RegistryEntry(service, observer, period));
    }

    public void addRegistryEntry(RegistryEntry entry) {
        registryEntries.add(entry);
    }

    public List<RegistryEntry> lookup() {
        List<RegistryEntry> result = new ArrayList<>();
        Date now = new Date();

        registryEntries.forEach(e -> {
            Date nextRun = e.getNextRunAt();
            if (nextRun == null || nextRun.equals(now)) {
                result.add(e);
            } else if (nextRun.before(now) && nextRun.after(new Date(nextRun.getTime() - e.getPeriod()))) {
                result.add(e);
            }
        });

        return result;
    }

    public boolean exists(WebService service, WebServiceObserver observer) {
        RegistryEntry registryEntry = new RegistryEntry(service, observer);
        for (RegistryEntry entry : registryEntries) {
            if (entry.equals(registryEntry)) {
                return true;
            }
        }
        return false;
    }

    public RegistryEntry findEntryByServiceAndObserver(WebService service, WebServiceObserver observer) {
        RegistryEntry registryEntry = new RegistryEntry(service, observer);
        for (RegistryEntry entry : registryEntries) {
            if (entry.equals(registryEntry)) {
                return entry;
            }
        }
        return null;
    }

}
