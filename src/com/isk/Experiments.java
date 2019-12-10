package com.isk;

import java.util.ArrayList;
import java.util.List;

public class Experiments {
    public static boolean verbosity = false;
    public static int repeatCount = 20;

    private static String crossoverTypeToString(int type) {
        switch(type) {
            case 0:
                return "One Point Crossover";
            case 1:
                return "Two Point Crossover";
            case 2:
                return "Uniform Crossover";
            case 3:
                return "Roulette Crossover";
            default:
                return "Wrong Crossover";
        }
    }

    public static void displayCurrentResult(SurvivedChromosomeData survivedChromosomeData, SettingsForGA settingsForGA) {
        System.out.println("GA RESULT:");
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        System.out.println("Fitness: " + survivedChromosomeData.getFitness());
        System.out.println("Genes (representing the percent of packed item)\tItem size\tPacked size\tItem price\tPacked price");
        int genesCount = survivedChromosomeData.getGenes().length;
        double totalSize = 0;
        double totalPrice = 0;
        for (int i=0; i<genesCount; i++) {
            double val = (survivedChromosomeData.getGenes())[i];
            double itemSize = possibleItems.get(i).getItemSize();
            double itemPrice = possibleItems.get(i).getItemPrice();
            double realItemSize = val * itemSize;
            double realItemPrice = val * itemPrice;
            System.out.println(val + "\t" + itemSize + "\t" + realItemSize + "\t" + itemPrice + "\t" + realItemPrice);
            totalSize += realItemSize;
            totalPrice += realItemPrice;
        }
        System.out.println("Total size: " + totalSize);
        System.out.println("Total price: " + totalPrice);
        System.out.println("Backpack capacity: " + AppMemory.getInstance().getBackpackCapacity());
        System.out.println("Used settings: ");
        System.out.println("Population size: " + settingsForGA.populationSize);
        System.out.println("Crossover type: " + crossoverTypeToString(settingsForGA.crossoverType));
        System.out.println("Crossover chance: " + settingsForGA.crossoverChance);
        System.out.println("Mutation chance: " + settingsForGA.mutationChance);
        System.out.println("Processing finished in " + survivedChromosomeData.getExecTime() + " seconds");
    }

    public static double countItemsSize(SurvivedChromosomeData survivedChromosomeData) {
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        int genesCount = survivedChromosomeData.getGenes().length;
        double totalSize = 0;
        for (int i=0; i<genesCount; i++) {
            double val = (survivedChromosomeData.getGenes())[i];
            double itemSize = possibleItems.get(i).getItemSize();
            double realItemSize = val * itemSize;
            totalSize += realItemSize;
        }
        return totalSize;
    }

    public static double countItemsPrice(SurvivedChromosomeData survivedChromosomeData) {
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        int genesCount = survivedChromosomeData.getGenes().length;
        double totalPrice = 0;
        for (int i=0; i<genesCount; i++) {
            double val = (survivedChromosomeData.getGenes())[i];
            double itemPrice = possibleItems.get(i).getItemPrice();
            double realItemPrice = val * itemPrice;
            totalPrice += realItemPrice;
        }
        return totalPrice;
    }

    public static boolean isOK (double totalSize) {
        double capacity = AppMemory.getInstance().getBackpackCapacity();
        if (totalSize > capacity) return false;
        else return true;
    }

    public static List<SurvivedChromosomeData> testCommon(String fileName) throws Exception {
        List<SurvivedChromosomeData> scoresList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wartość spakowanych itemków\tWaga spakowanych itemków\tCzas pakowania\tCzy poprawne\r\n");
        SettingsForGA settingsForGA = new SettingsForGA();
        for (int rp=0; rp<repeatCount*8; rp++) {
            SurvivedChromosomeData score = GACurveFitX.performGA(settingsForGA);
            if (score == null) continue;
            double totalPrice = countItemsPrice(score);
            double totalSize = countItemsSize(score);
            double execTime = score.getExecTime();
            int isOK = isOK(totalSize)?1:0;
            stringBuilder.append(
                    totalPrice + "\t"
                    + totalSize + "\t"
                    + execTime + "\t"
                    + isOK
                    + "\r\n");
            Main.testCommonProgress++;
            if (verbosity) displayCurrentResult(score, settingsForGA);
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }

    public static List<SurvivedChromosomeData> testPopulation(String fileName) throws Exception {
        List<SurvivedChromosomeData> scoresList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wartość spakowanych itemków\tWaga spakowanych itemków\tCzas pakowania\tCzy poprawne\tRozmiar populacji\r\n");
        SettingsForGA settingsForGA = new SettingsForGA();
        for (int iPop = 5; iPop < 500; iPop +=5) {
            settingsForGA.populationSize=iPop;
            for (int rp=0; rp<repeatCount; rp++) {
                SurvivedChromosomeData score = GACurveFitX.performGA(settingsForGA);
                if (score == null) continue;
                double totalPrice = countItemsPrice(score);
                double totalSize = countItemsSize(score);
                double execTime = score.getExecTime();
                int isOK = isOK(totalSize)?1:0;
                stringBuilder.append(
                        totalPrice + "\t"
                        + totalSize + "\t"
                        + execTime + "\t"
                        + isOK + "\t"
                        + iPop
                        + "\r\n");
                Main.testPopulationProgress++;
                if (verbosity) displayCurrentResult(score, settingsForGA);
            }
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }

    public static List<SurvivedChromosomeData> testDifferentCrossovers(String fileName) throws Exception {
        List<SurvivedChromosomeData> scoresList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wartość spakowanych itemków\tWaga spakowanych itemków\tCzas pakowania\tCzy poprawne\tTyp crossovera\r\n");
        SettingsForGA settingsForGA = new SettingsForGA();
        for (int iType = 0; iType <= 3; iType++) {
            settingsForGA.crossoverType = iType;
            for (int rp=0; rp<repeatCount; rp++) {
                SurvivedChromosomeData score = GACurveFitX.performGA(settingsForGA);
                if (score == null) continue;
                double totalPrice = countItemsPrice(score);
                double totalSize = countItemsSize(score);
                double execTime = score.getExecTime();
                int isOK = isOK(totalSize)?1:0;
                stringBuilder.append(
                        totalPrice + "\t"
                        + totalSize + "\t"
                        + execTime + "\t"
                        + isOK + "\t"
                        + crossoverTypeToString(iType)
                        +"\r\n");
                Main.testDifferentCrossoversProgress++;
                if (verbosity) displayCurrentResult(score, settingsForGA);
            }
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }

    public static List<SurvivedChromosomeData> testCrossoverChance(String fileName) throws Exception {
        List<SurvivedChromosomeData> scoresList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wartość spakowanych itemków\tWaga spakowanych itemków\tCzas pakowania\tCzy poprawne\tSzansa crossovera\r\n");
        SettingsForGA settingsForGA = new SettingsForGA();
        for (double iCrossChance = 0.05f; iCrossChance < 1.0f; iCrossChance += 0.05f) {
            settingsForGA.crossoverChance = iCrossChance;
            for (int rp=0; rp<repeatCount; rp++) {
                SurvivedChromosomeData score = GACurveFitX.performGA(settingsForGA);
                if (score == null) continue;
                double totalPrice = countItemsPrice(score);
                double totalSize = countItemsSize(score);
                double execTime = score.getExecTime();
                int isOK = isOK(totalSize)?1:0;
                stringBuilder.append(
                        totalPrice + "\t"
                        + totalSize + "\t"
                        + execTime + "\t"
                        + isOK + "\t"
                        + iCrossChance
                        + "\r\n");
                Main.testCrossoverChanceProgress++;
                if (verbosity) displayCurrentResult(score, settingsForGA);
            }
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }

    public static List<SurvivedChromosomeData> testMutationChance(String fileName) throws Exception {
        List<SurvivedChromosomeData> scoresList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wartość spakowanych itemków\tWaga spakowanych itemków\tCzas pakowania\tCzy poprawne\tSzansa mutacji\r\n");
        SettingsForGA settingsForGA = new SettingsForGA();
        for (double iMutChance = 0.05f; iMutChance < 1.0f; iMutChance += 0.01f) {
            settingsForGA.mutationChance=iMutChance;
            for (int rp=0; rp<repeatCount; rp++) {
                SurvivedChromosomeData score = GACurveFitX.performGA(settingsForGA);
                if (score == null) continue;
                double totalPrice = countItemsPrice(score);
                double totalSize = countItemsSize(score);
                double execTime = score.getExecTime();
                int isOK = isOK(totalSize)?1:0;
                stringBuilder.append(
                        totalPrice + "\t"
                        + totalSize + "\t"
                        + execTime + "\t"
                        + isOK + "\t"
                        + iMutChance
                        + "\r\n");
                Main.testMutationChanceProgress++;
                if (verbosity) displayCurrentResult(score, settingsForGA);
            }
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }

    public static List<SurvivedChromosomeData> testAIO(String fileName) throws Exception {
        List<SurvivedChromosomeData> scoresList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wartość spakowanych itemków\tWaga spakowanych itemków\tCzas pakowania\tCzy poprawne\tTyp crossovera\tRozmiar populacji\tSzansa na crossover\tSzansa na mutację\r\n");
        SettingsForGA settingsForGA = new SettingsForGA();
        for (int iType = 0; iType <= 3; iType++) {
            settingsForGA.crossoverType = iType;
            for (int iPop = 5; iPop <= 500; iPop += 5) {
                settingsForGA.populationSize = iPop;
                for (double iCrossChance = 0.05f; iCrossChance < 1.0f; iCrossChance += 0.05f) {
                    settingsForGA.crossoverChance = iCrossChance;
                    for (double iMutChance = 0.05f; iMutChance < 1.0f; iMutChance += 0.01f) {
                        settingsForGA.mutationChance = iMutChance;
                        for (int rp = 0; rp < repeatCount; rp++) {
                            SurvivedChromosomeData score = GACurveFitX.performGA(settingsForGA);
                            if (score == null) continue;
                            double totalPrice = countItemsPrice(score);
                            double totalSize = countItemsSize(score);
                            double execTime = score.getExecTime();
                            int isOK = isOK(totalSize) ? 1 : 0;
                            stringBuilder.append(
                                    totalPrice + "\t"
                                    + totalSize + "\t"
                                    + execTime + "\t"
                                    + isOK + "\t"
                                    + crossoverTypeToString(iType) + "\t"
                                    + iPop + "\t"
                                    + iCrossChance + "\t"
                                    + iMutChance
                                    + "\r\n");
                            Main.testAioProgress++;
                            if (verbosity) displayCurrentResult(score, settingsForGA);
                        }
                    }
                }
            }
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }
}
