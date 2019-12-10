package com.isk;

import java.util.List;
import java.util.StringTokenizer;

public class FileParser {
    public static void parseFile(String fileName) throws Exception {
        String file = FileManager.readFile("input.txt");

        StringTokenizer fileTokenizer = new StringTokenizer(file, "\r\n");
        if (!fileTokenizer.hasMoreTokens()) throw new Exception("File structure invalid - backpack capacity not found!");
        String capacity = fileTokenizer.nextToken();
        capacity = capacity.substring(1, capacity.length()-1);
        AppMemory.getInstance().setBackpackCapacity(Double.valueOf(capacity));

        List<PossibleItem> possibleItems = AppMemory.getInstance().getPossibleItems();

        while (fileTokenizer.hasMoreTokens()) {
            possibleItems.add(FileParser.parsePossibleItemEntry(fileTokenizer.nextToken()));
        }

    }

    private static PossibleItem parsePossibleItemEntry (String entry) {
        entry = entry.substring(1, entry.length()-1);
        StringTokenizer entryTokenizer = new StringTokenizer(entry, "><");
        int itemId = Integer.valueOf(entryTokenizer.nextToken());
        double itemSize = Double.valueOf(entryTokenizer.nextToken());
        double itemPrice = Double.valueOf(entryTokenizer.nextToken());
        return new PossibleItem(itemId, itemSize, itemPrice);
    }
}
