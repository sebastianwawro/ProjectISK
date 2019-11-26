package com.isk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.softtechdesign.ga.GAException;
import com.softtechdesign.ga.examples.*;
import io.jenetics.knapsack.Knapsack;

public class Main {

    public static void main(String[] args) throws Exception {
        FileParser.parseFile("input.txt");
        System.out.println("Backspace capacity: " + AppMemory.getInstance().getBackspaceCapacity() + "\n\n");
        for (PossibleItem item : AppMemory.getInstance().getPossibleItems()) {
            System.out.println("Item:\n"+item.getItemId()+"\n"+item.getItemSize()+"\n"+item.getItemPrice()+"\n\n");
        }

        String out = Knapsack.solveKnapsack(AppMemory.getInstance().getPossibleItems().size(),
                PossibleItem.getItemSizesTable(),
                PossibleItem.getItemPricesTable(),
                AppMemory.getInstance().getBackspaceCapacity(),
                5000,
                50,
                0.05,
                0.20,
                1000,
                1000,
                true);

        System.out.println("Solved!");
        System.out.println("Taken items: ");
        List<Integer> takenItemsIds = PossibleItem.getTakenItemIdsTable(out);
        for (Integer itemId : takenItemsIds) {
            System.out.print(itemId + " ");
        }
        System.out.println("");
        System.out.println("Final value: " + PossibleItem.getFinalValue(takenItemsIds));
        System.out.println("Final size: " + PossibleItem.getFinalSize(takenItemsIds));

        //GASalesman.main(null);
        //GAMaze.main(null);
        //GATrigFunc.main(null);
	// write your code here
    }
}
