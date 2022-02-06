package com.jackhodgkiss;

/**
 * This class implements the ordered placement algorithm for the circle packing problem. This algorithm works by placing
 * the circles in the order they were provided.
 */
public class OrderedSolver extends Solver {

    /**
     * Construct a new instance of OrderedSolver with the set of radii provided.
     * @param Radii Array of radii used by each circle within the problem to be solved.
     */
    public OrderedSolver(int[] Radii) {
        super(Radii);
    }

    /**
     * This function shall solve the circle placement problem by placing them in the order they are provided by the
     * 'user'.
     * @return Array of circles placed in line with ordered placement algorithm.
     */
    @Override
    public Circle[][] Solve() {
        Circle[][] Result = new Circle[1][this.Radii.length];
        Circle[] Circles = Utility.PositionCircles(this.Radii);
        for(int i = 0; i < this.Radii.length; i++) {
            Result[0][i] = Circles[i];
        }
        return Result;
    }
}
