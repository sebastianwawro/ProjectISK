package com.isk;

import java.util.ArrayList;
import java.util.List;

public class Experiments {
    private static boolean verbosity = false;
    private static int repeatCount = 20;

    public static void displayCurrentResult(SurvivedChromosomeData survivedChromosomeData) {
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        System.out.println("Fitness: " + survivedChromosomeData.getFitness());
        System.out.println("Genes: ");
        int genesCount = survivedChromosomeData.getGenes().length;
        double totalSize = 0;
        double totalPrice = 0;
        for (int i=0; i<genesCount; i++) {
            double val = (survivedChromosomeData.getGenes())[i];
            double itemSize = possibleItems.get(i).getItemSize();
            double itemPrice = possibleItems.get(i).getItemPrice();
            double realItemSize = val * itemSize;
            double realItemPrice = val * itemPrice;
            System.out.println(val + "  " + itemSize + "  " + realItemSize + "  " + itemPrice + "  " + realItemPrice);
            totalSize += realItemSize;
            totalPrice += realItemPrice;
        }
        System.out.println("Total size: " + totalSize);
        System.out.println("Total price: " + totalPrice);
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
        double capacity = AppMemory.getInstance().getBackspaceCapacity();
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
            double totalPrice = countItemsPrice(score);
            double totalSize = countItemsSize(score);
            double execTime = score.getExecTime();
            int isOK = isOK(totalSize)?1:0;
            stringBuilder.append(totalPrice+"\t"+totalSize+"\t"+execTime+"\t"+isOK);
            if (verbosity) displayCurrentResult(score);
        }
        String result = stringBuilder.toString();
        FileManager.writeFile(fileName, result);
        return scoresList;
    }
}
