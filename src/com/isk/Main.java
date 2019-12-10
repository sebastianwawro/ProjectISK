package com.isk;

import java.util.List;

//import com.softtechdesign.ga.examples.*;
import io.jenetics.knapsack.Knapsack;

public class Main {
    public static int testCommonProgress = 0;
    public static int testPopulationProgress = 0;
    public static int testDifferentCrossoversProgress = 0;
    public static int testCrossoverChanceProgress = 0;
    public static int testMutationChanceProgress = 0;
    public static int testAioProgress = 0;
    public static int testCommonMax = Experiments.repeatCount * 8;
    public static int testPopulationMax = Experiments.repeatCount * ((500-10)/10);
    public static int testDifferentCrossoversMax = Experiments.repeatCount * 4;
    public static int testCrossoverChanceMax = Experiments.repeatCount * (int)((1.0f - 0.05f) / 0.05f);
    public static int testMutationChanceMax = Experiments.repeatCount * (int)((1.0f - 0.05f) / 0.01f);
    public static int testAioMax = Experiments.repeatCount * 4 * ((500-10)/10) * (int)((1.0f - 0.05f) / 0.05f) * (int)((1.0f - 0.05f) / 0.01f);
    public static Thread statusThread = null;
    private static boolean started = false;

    public static void main(String[] args) throws Exception {
        //solveUsingJenetics();
        //exampleGA();
        prepareStatusThread();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Click enter to begin!");
        int x = System.in.read();
        started = true;
        if (x == 97 || x == 65) solveUsingGA_Async();
        else solveUsingGA_Sync();
    }

    public static void prepareStatusThread() {
        statusThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!started) {
                        try {
                            Thread.sleep(3000);
                        }
                        catch (Exception e) {e.printStackTrace();}
                        continue;
                    }
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    System.out.println("STATUS:");
                    System.out.println("Test Common: " + testCommonProgress + "/" + testCommonMax);
                    System.out.println("Test Population: " + testPopulationProgress + "/" + testPopulationMax);
                    System.out.println("Test Different Crossovers: " + testDifferentCrossoversProgress + "/" + testDifferentCrossoversMax);
                    System.out.println("Test Crossover Chance: " + testCrossoverChanceProgress + "/" + testCrossoverChanceMax);
                    System.out.println("Test Mutation Chance: " + testMutationChanceProgress + "/" + testMutationChanceMax);
                    //System.out.println("Test AIO: " + testAioProgress + "/" + testAioMax);
                    System.out.println();
                    if (testCommonProgress >= testCommonMax
                            && testPopulationProgress >= testPopulationMax
                            && testDifferentCrossoversProgress >= testDifferentCrossoversMax
                            && testCrossoverChanceProgress >= testCrossoverChanceMax
                            && testMutationChanceProgress >= testMutationChanceMax
                            //&& testAioProgress >= testAioMax
                    ) break;
                    try {
                        Thread.sleep(7000);
                    }
                    catch (Exception e) {e.printStackTrace();}
                }
            }
        });
        if (!Experiments.verbosity) statusThread.start();
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
            //Experiments.testAIO("MacierzTestAIO.txt");
        }
        catch (Exception e) {
            System.out.println("Test AIO failed, exception thrown: ");
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
                    System.out.println("Test AIO failed, exception thrown: ");
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
