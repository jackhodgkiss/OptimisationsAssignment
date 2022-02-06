package com.jackhodgkiss;

import processing.core.PApplet;

import java.util.Arrays;

/**
 * This class with be responsible for representing a visually a given solution to the current problem set. This class
 * will accept a solution set and work to place them appropriately without any overlap or intersection.
 */
public class Solution {

    protected Circle[] Circles;

    protected Circle BoundaryCircle; // TODO: Move this within the array of circles at the end?

    /**
     * Construct a new instance of the Solution with the supplied solution set.
     * @param Circles Array of circles that form a valid solution.
     */
    public Solution(Circle[] Circles) {
        this.SetCircles(Circles);
    }

    /**
     * Draw the circles in their arrangement with the boundary circle behind them.
     * @param Parent PApplet instance required to access the drawing capabilities of processing.
     */
    public void Draw(PApplet Parent) {
        Parent.pushMatrix();
        Parent.translate(Parent.width / 2, Parent.height / 2);
        BoundaryCircle.Draw(Parent);
        for(Circle C : Circles) {
            C.Draw(Parent);
        }
        Parent.popMatrix();
    }

    public void SetCircles(Circle[] Circles) {
        this.Circles = Circles;
        this.BoundaryCircle = new Circle(Utility.CalculateBoundingRadius(this.Circles), 0, 0, false);
    }

    /**
     * Get the fitness value of the solution.
     * @return Fitness value is the radius of the boundary circle. Smaller the fitness value the better.
     */
    public int GetFitness() {
        return this.BoundaryCircle.GetRadius();
    }

    @Override
    public String toString() {
        int[] Radii = new int[this.Circles.length];
        for(int i = 0; i < Radii.length; i++) {
            Radii[i] = this.Circles[i].GetRadius();
        }
        return String.format("%s, %d", Arrays.toString(Radii), this.GetFitness());
    }
}
