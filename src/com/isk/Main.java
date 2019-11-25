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
                500,
                5,
                0.115,
                0.16,
                7,
                100,
                true);

        System.out.println("Solved!");
        System.out.println("Taken items: ");
        for (Integer itemId : PossibleItem.getTakenItemIdsTable(out)) {
            System.out.print(itemId + " ");
        }

        //GASalesman.main(null);
        //GAMaze.main(null);
        //GATrigFunc.main(null);
	// write your code here
    }
}
