package com.isk;

import java.util.ArrayList;
import java.util.List;

public class AppMemory {
    private static AppMemory _instance;
    private List<PossibleItem> possibleItems;
    private double backspaceCapacity;

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

    public double getBackspaceCapacity() {
        return backspaceCapacity;
    }

    public void setBackspaceCapacity(double backspaceCapacity) {
        this.backspaceCapacity = backspaceCapacity;
    }
}
