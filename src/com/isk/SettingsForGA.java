package com.isk;

import com.softtechdesign.ga.Crossover;

public class SettingsForGA {
    public int populationSize;
    public int crossoverType;
    public double crossoverChance;
    public double mutationChance;

    public SettingsForGA() {
        populationSize = 500;
        crossoverType = Crossover.ctTwoPoint;
        crossoverChance = 0.20f;
        mutationChance = 0.05f;
    }
}
