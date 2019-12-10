package com.isk;

public class SurvivedChromosomeData {
    private double fitness;
    private double[] genes;
    private double execTime;

    public SurvivedChromosomeData(double fitness, double[] genes, double execTime) {
        this.fitness = fitness;
        this.genes = genes;
        this.execTime = execTime;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    public double getExecTime() {
        return execTime;
    }

    public void setExecTime(double execTime) {
        this.execTime = execTime;
    }
}
