package com.jackhodgkiss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * This class implements the greedy algorithm for the circle packing problem. This algorithm works by
 * placing the circles in ascending order.
 */
public class GreedySolver extends Solver {

    public GreedySolver(int[] Radii) {
        super(Radii);
    }

    /**
     * This function shall solve the circle placement problem by placing them in ascending order.
     * @return Array of circles placed in line with greedy placement algorithm.
     */
    public Circle[][] Solve() {
        Circle[][] Result = new Circle[1][this.Radii.length];
        ArrayList<Integer> SortedRadii =
                new ArrayList<>(Arrays.stream(this.Radii).boxed().collect(Collectors.toList()));
        SortedRadii.sort(Comparator.comparingInt(a -> a));
        for(int i = 0; i < this.Radii.length; i++) {
            this.Radii[i] = SortedRadii.get(i);
        }
        Circle[] Circles = Utility.PositionCircles(this.Radii);
        for(int i = 0; i < this.Radii.length; i++) {
            Result[0][i] = Circles[i];
        }
        return Result;
    }
}
