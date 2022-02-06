package com.jackhodgkiss;

import processing.core.PApplet;

/**
 * This class will be responsible for representing a 2D position within the solution space.
 */
public class Position2D {

    private int X;

    private int Y;

    /**
     * Construct a new instance of the Position2D with the supplied parameters.
     * @param X Point on the X-axis.
     * @param Y Point on the Y-axis.
     */
    public Position2D(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Calculate the distance between 'this' position and another position.
     * @param Other Another instance of Position2D in which you wish to calculate the distance between.
     * @return The distance between the two points.
     */
    public int Distance(Position2D Other) {
        return (int) PApplet.dist(this.X, this.Y, Other.GetX(), Other.GetY());
    }

    /**
     * Calculate the distance between 'this' position and a set of coordinates.
     * @param OtherX Point on the X-axis of which you want to find the distance between.
     * @param OtherY Point on the Y-axis of which you want to find the distance between.
     * @return The distance between the two points
     */
    public int Distance(int OtherX, int OtherY) {
        return (int) PApplet.dist(this.X, this.Y, OtherX, OtherY);
    }

    public int GetX() {
        return this.X;
    }

    public int GetY() {
        return this.Y;
    }

    @Override
    public String toString() {
        return String.format("X: %d Y: %d", GetX(), GetY());
    }
}
