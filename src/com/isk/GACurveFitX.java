package com.isk;

import com.softtechdesign.ga.*;

import java.util.List;

public class GACurveFitX extends GAFloat
{
    int itemsCount;
    List<PossibleItem> possibleItems;
    double capacity;

    public GACurveFitX(
            int itemsCount,
            List<PossibleItem> possibleItems,
            double capacity,
            int populationSize,
            int crossoverType,
            double crossoverChance,
            double mutationChance
    ) throws GAException
    {
        super(itemsCount, //chromosome dim (number of genes)
                populationSize, //population of chromosomes (DEFAULT 100)
                crossoverChance, //crossover probability (DEFAULT 0.7)
              6, //random selection chance % (regardless of fitness)
              3000, //stop after this many generations
              10, //num prelim runs (to build good breeding stock for final--full run)
              20, //max prelim generations
                mutationChance, //chromosome mutation prob. (DEFAULT 0.1)
                crossoverType, //crossover type
              2, //num decimal pts of precision
              //if chrom has 3 genes and 2 decimal place, numbers look like "0.12"
              true, //only consider positive float numbers?
              true); //compute statistics?

        this.possibleItems = possibleItems;
        this.itemsCount = possibleItems.size();
        this.capacity = capacity;
    }

    protected double getFitness(int iChromIndex)
    {
        double packedValue=0;
        double packedWeight=0;
        for (int iGene=0; iGene<itemsCount; iGene++) {
            double val = this.getChromosome(iChromIndex).getGene(iGene);
            if (val > 1) return -(Integer.MAX_VALUE);
            packedValue += this.possibleItems.get(iGene).getItemPrice() * val;
            packedWeight += this.possibleItems.get(iGene).getItemSize() * val;
        }
        return (packedWeight<=capacity) ? (packedValue * (packedWeight / capacity)) : (-packedWeight);
    }

    public static SurvivedChromosomeData performGA(SettingsForGA settingsForGA)
    {
        if(Experiments.verbosity) System.out.println("Packing backpack...");
        try
        {
            List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
            int itemsCount = possibleItems.size();
            GACurveFitX curveFit = new GACurveFitX(
                    itemsCount,
                    possibleItems,
                    AppMemory.getInstance().getBackpackCapacity(),
                    settingsForGA.populationSize,
                    settingsForGA.crossoverType,
                    settingsForGA.crossoverChance,
                    settingsForGA.mutationChance);
            Thread threadCurveFit = new Thread(curveFit);
            long startTime = System.currentTimeMillis();
            threadCurveFit.start();
            threadCurveFit.join();
            long endTime = System.currentTimeMillis();
            double elapsed = (double)(endTime-startTime);
            double execTime = elapsed/1000.0;
            if (curveFit.extBestChromosomeFitness != -1) {
                return new SurvivedChromosomeData(curveFit.extBestChromosomeFitness, curveFit.extBestChromosomeGenes, execTime);
            }
            else {
                if(Experiments.verbosity) System.out.println("fail!");
                return null;
            }
        }
        catch (GAException gae)
        {
            System.out.println(gae.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}