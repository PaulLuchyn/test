package com.pluchyn.testproject.util;

import com.pluchyn.testproject.impl.WebService;
import com.pluchyn.testproject.impl.WebServiceObserver;

import java.util.ArrayList;

public class Registry {

    private ArrayList<RegistryEntry> registryEntries = new ArrayList<>();

    public void addRegistryEntry(RegistryEntry entry) {
        registryEntries.add(entry);
    }

    public RegistryEntry getByNearestRun() {
        registryEntries.sort((e1, e2) -> e1.getNextRunAt().compareTo(e2.getNextRunAt()));
        return registryEntries.get(0);
    }

    public boolean exists(WebService service, WebServiceObserver observer) {
        RegistryEntry registryEntry = new RegistryEntry(service,observer);
        for (RegistryEntry entry : registryEntries) {
            if (entry.equals(registryEntry)) {
                return true;
            }
        }
        return false;
    }

    public RegistryEntry findEntryByServiceAndObserver(WebService service, WebServiceObserver observer) {
        RegistryEntry registryEntry = new RegistryEntry(service,observer);
        for (RegistryEntry entry : registryEntries) {
            if (entry.equals(registryEntry)) {
                return entry;
            }
        }
        return null;
    }

}
