package com.jackhodgkiss;

/**
 * This class will be responsible for acting as basis for the various algorithms used within this application. It is
 * expected that children of this class will override key functions in order to implement their own algorithms to create
 * solutions to the circle packing problem explored within this application.
 */
public class Solver {

    protected int[] Radii;

    /**
     * Construct a new instance of Solver with the set of radii provided.
     * @param Radii Array of radii used by each circle within the problem to be solved.
     */
    public Solver(int[] Radii) {
        this.Radii = Radii;
    }

    /**
     * To be implemented via children.
     * @return 2D array of circles that are solutions to computed within this function. Algorithms may use more that one
     * row if they produce more than one solution per call.
     */
    public Circle[][] Solve() {
        return null;
    }

}
