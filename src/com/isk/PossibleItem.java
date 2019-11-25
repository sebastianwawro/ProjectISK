package com.isk;

import java.util.ArrayList;
import java.util.List;

public class PossibleItem {
    private int itemId;
    private double itemSize;
    private double itemPrice;

    public PossibleItem(int itemId, double itemSize, double itemPrice) {
        this.itemId = itemId;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getItemSize() {
        return itemSize;
    }

    public void setItemSize(double itemSize) {
        this.itemSize = itemSize;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public static double[] getItemSizesTable() {
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        double[] itemSizes = new double[possibleItems.size()];
        int i=0;
        for (PossibleItem possibleItem : possibleItems) {
            itemSizes[i++]=possibleItem.getItemSize();
        }
        return itemSizes;
    }

    public static double[] getItemPricesTable() {
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        double[] itemPrices = new double[possibleItems.size()];
        int i=0;
        for (PossibleItem possibleItem : possibleItems) {
            itemPrices[i++]=possibleItem.getItemPrice();
        }
        return itemPrices;
    }

    public static int[] getItemIdsTable() {
        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();
        int[] itemIds = new int[possibleItems.size()];
        int i=0;
        for (PossibleItem possibleItem : possibleItems) {
            itemIds[i++]=possibleItem.getItemId();
        }
        return itemIds;
    }

    public static List<Integer> getTakenItemIdsTable(String outputToParse) {
        int[] itemIds = getItemIdsTable();
        List<Integer> takenItemIds = new ArrayList<>();
        int i = 0;
        int itemsCount = itemIds.length;
        for (char c : outputToParse.toCharArray()) {
            if (i >= itemsCount) break;
            switch (c) {
                case '0':
                    i++;
                    break;
                case '1':
                    takenItemIds.add(itemIds[i++]);
                    break;
                default:
                    break;
            }
        }
        return takenItemIds;
    }
}
