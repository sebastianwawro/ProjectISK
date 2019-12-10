package com.isk;

import java.util.ArrayList;
import java.util.List;

public class AppMemory {
    private static AppMemory _instance;
    private List<PossibleItem> possibleItems;
    private double backpackCapacity;

    private AppMemory(){}

    synchronized public static AppMemory getInstance() {
        if (_instance == null) {
            _instance = new AppMemory();
            _instance.possibleItems = new ArrayList<PossibleItem>();
        }
        return _instance;
    }

    public List<PossibleItem> getPossibleItems() {
        return possibleItems;
    }

    public double getBackpackCapacity() {
        return backpackCapacity;
    }

    public void setBackpackCapacity(double backpackCapacity) {
        this.backpackCapacity = backpackCapacity;
    }
}
