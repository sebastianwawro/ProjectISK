package com.isk;

import java.util.List;

//import com.softtechdesign.ga.examples.*;
import io.jenetics.knapsack.Knapsack;

public class Main {

    public static void main(String[] args) throws Exception {
        //solveUsingJenetics();
        exampleGA();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Click enter to begin!");
        int x = System.in.read();
        if (x == 97 || x == 65) solveUsingGA_Async();
        else solveUsingGA_Sync();
    }

    public static void exampleGA() throws Exception {
        FileParser.parseFile("input.txt");
        System.out.println("Backpack capacity: " + AppMemory.getInstance().getBackpackCapacity() + "\n\n");
        for (PossibleItem item : AppMemory.getInstance().getPossibleItems()) {
            System.out.println("Item:\n"+item.getItemId()+"\n"+item.getItemSize()+"\n"+item.getItemPrice()+"\n\n");
        }

        SettingsForGA settingsForGA = new SettingsForGA();
        SurvivedChromosomeData survivedChromosomeData = GACurveFitX.performGA(settingsForGA);
        if (survivedChromosomeData == null) throw new Exception("failed so much");
        Experiments.displayCurrentResult(survivedChromosomeData, settingsForGA);
    }


    public static void solveUsingGA_Sync() throws Exception {
        FileParser.parseFile("input.txt");
        System.out.println("Backpack capacity: " + AppMemory.getInstance().getBackpackCapacity() + "\n\n");
        for (PossibleItem item : AppMemory.getInstance().getPossibleItems()) {
            System.out.println("Item:\n"+item.getItemId()+"\n"+item.getItemSize()+"\n"+item.getItemPrice()+"\n\n");
        }

        try {
            Experiments.testCommon("MacierzTestZwykly.txt");
        }
        catch (Exception e) {
            System.out.println("Test Common failed, exception thrown: ");
            e.printStackTrace();
        }
        try {
            Experiments.testPopulation("MacierzTestPopulacje.txt");
        }
        catch (Exception e) {
            System.out.println("Test Population failed, exception thrown: ");
            e.printStackTrace();
        }
        try {
            Experiments.testDifferentCrossovers("MacierzRozneCrossovery.txt");
        }
        catch (Exception e) {
            System.out.println("Test Different Crossovers failed, exception thrown: ");
            e.printStackTrace();
        }
        try {
            Experiments.testCrossoverChance("MacierzTestSzansaCrossovera.txt");
        }
        catch (Exception e) {
            System.out.println("Test Crossover Chance failed, exception thrown: ");
            e.printStackTrace();
        }
        try {
            Experiments.testMutationChance("MacierzTestSzansaMutacji.txt");
        }
        catch (Exception e) {
            System.out.println("Test Mutation Chance failed, exception thrown: ");
            e.printStackTrace();
        }
        try {
            Experiments.testAIO("MacierzTestAIO.txt");
        }
        catch (Exception e) {
            System.out.println("Test Common failed, exception thrown: ");
            e.printStackTrace();
        }

        System.out.println("Kuniec");
        Thread.sleep(5000);
    }

    public static void solveUsingGA_Async() throws Exception {
        FileParser.parseFile("input.txt");
        System.out.println("Backpack capacity: " + AppMemory.getInstance().getBackpackCapacity() + "\n\n");
        for (PossibleItem item : AppMemory.getInstance().getPossibleItems()) {
            System.out.println("Item:\n"+item.getItemId()+"\n"+item.getItemSize()+"\n"+item.getItemPrice()+"\n\n");
        }

        Thread testCommonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Experiments.testCommon("MacierzTestZwykly.txt");
                }
                catch (Exception e) {
                    System.out.println("Test Common failed, exception thrown: ");
                    e.printStackTrace();
                }
            }
        });
        Thread testPopulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Experiments.testPopulation("MacierzTestPopulacje.txt");
                }
                catch (Exception e) {
                    System.out.println("Test Population failed, exception thrown: ");
                    e.printStackTrace();
                }
            }
        });
        Thread testDifferentCrossoversThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Experiments.testDifferentCrossovers("MacierzRozneCrossovery.txt");
                }
                catch (Exception e) {
                    System.out.println("Test Different Crossovers failed, exception thrown: ");
                    e.printStackTrace();
                }
            }
        });
        Thread testCrossoverChanceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Experiments.testCrossoverChance("MacierzTestSzansaCrossovera.txt");
                }
                catch (Exception e) {
                    System.out.println("Test Crossover Chance failed, exception thrown: ");
                    e.printStackTrace();
                }
            }
        });
        Thread testMutationChanceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Experiments.testMutationChance("MacierzTestSzansaMutacji.txt");
                }
                catch (Exception e) {
                    System.out.println("Test Mutation Chance failed, exception thrown: ");
                    e.printStackTrace();
                }
            }
        });
        Thread testAioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Experiments.testAIO("MacierzTestAIO.txt");
                }
                catch (Exception e) {
                    System.out.println("Test Common failed, exception thrown: ");
                    e.printStackTrace();
                }
            }
        });

        testCommonThread.start();
        testPopulationThread.start();
        testDifferentCrossoversThread.start();
        testCrossoverChanceThread.start();
        testMutationChanceThread.start();
        //testAioThread.start();

        testCommonThread.join();
        testPopulationThread.join();
        testDifferentCrossoversThread.join();
        testCrossoverChanceThread.join();
        testMutationChanceThread.join();
        //testAioThread.join();

        System.out.println("Kuniec");
        Thread.sleep(5000);
    }

    public static void solveUsingJenetics() throws Exception {
        FileParser.parseFile("input.txt");
        System.out.println("Backpack capacity: " + AppMemory.getInstance().getBackpackCapacity() + "\n\n");
        for (PossibleItem item : AppMemory.getInstance().getPossibleItems()) {
            System.out.println("Item:\n"+item.getItemId()+"\n"+item.getItemSize()+"\n"+item.getItemPrice()+"\n\n");
        }

        String out = Knapsack.solveKnapsack(AppMemory.getInstance().getPossibleItems().size(),
                PossibleItem.getItemSizesTable(),
                PossibleItem.getItemPricesTable(),
                AppMemory.getInstance().getBackpackCapacity(),
                5000,
                50,
                0.05,
                0.20,
                1000,
                1000,
                false);

        System.out.println("Solved!");
        System.out.println("Taken items: ");
        List<Integer> takenItemsIds = PossibleItem.getTakenItemIdsTable(out);
        for (Integer itemId : takenItemsIds) {
            System.out.print(itemId + " ");
        }
        System.out.println("");
        System.out.println("Final value: " + PossibleItem.getFinalValue(takenItemsIds));
        System.out.println("Final size: " + PossibleItem.getFinalSize(takenItemsIds));
    }
}
