package com.jackhodgkiss;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class will act a home for various functions that are required throughout the application.
 */
public class Utility {

    /**
     * This function is responsible for placing circles at the best viable position within the simulation space. It uses
     * an array of radii placing each of the circles within the order found. A viable position is one in which the
     * circle doesn't intersect with any of the other circles within the space. Whereas the best viable position is one
     * that satisfies the previous requirement whilst being placed the closest to the origin (0, 0) as possible.
     *
     * The placement algorithm works by taking an array of radii and for each of them doing the following;
     * - Looking back at each of the previous circles placed before it and walking around the circumference of them
     *   calculating a potential position.
     * - With this potential position it will look back at all of the other circles within the space again this time
     *   checking collisions.
     * - If a collision is detected then the position is discarded otherwise it is saved.
     * - Once all of the potential positions have been exhausted a comparision function is applied to the container to
     *   find the best position; one where the circle is positioned the closest to the center.
     * - Once the best position has been identified a new circle is constructed and placed within the array of circles
     *   for this solution.
     * - This is done for each of the radii found within the array.
     * @param Radii int array of radii used by each of the circles to placed.
     * @return Array of circles that have been initialised with the appropriate coordinates.
     */
    public static Circle[] PositionCircles(int[] Radii) {
        Circle[] Circles = new Circle[Radii.length];
        ArrayList<Position2D> ViablePositions = new ArrayList<>();
        int X = 0, Y = 0, OriginX = 0, OriginY = 0;
        boolean isValid;
        for(int i = 0; i < Radii.length; i++) {
            if(i == 0) {
                Circles[i] = new Circle(Radii[i], OriginX, OriginY, true);
                continue;
            }
            for(int j = 0; j < i; j++) {
                for(int Angle = 0; Angle < 360; Angle++) {
                    isValid = true;
                    X = Circles[j].GetPosition().GetX() + (int) (Math.cos(Math.toRadians(Angle))
                            * (Radii[i] + Radii[j] + 1));
                    Y = Circles[j].GetPosition().GetY() + (int) (Math.sin(Math.toRadians(Angle))
                            * (Radii[i] + Radii[j] + 1));
                    for(int k = 0; k < i; k++) {
                        if(!isValid) break;
                        int Distance = (int) PApplet.dist(X, Y, Circles[k].GetPosition().GetX(),
                                Circles[k].GetPosition().GetY());
                        if(Distance < Radii[i] + Radii[k]) {
                            isValid = false;
                        }
                    }
                    if(isValid) ViablePositions.add(new Position2D(X, Y));
                }
            }
            ViablePositions.sort(Comparator.comparingInt(a -> a.Distance(OriginX, OriginY)));
            Circles[i] = new Circle(Radii[i], ViablePositions.get(0), true);
            ViablePositions.clear();
        }
        return Circles;
    }


    /**
     * This function calculates the bounding radius of the solution space. This is done by copying the array of circles
     * into an ArrayList then sorting them on distance. Once done the final element within the list has the largest
     * distance and therefore it and its distance and radius can be used to calculate the bounding radius.
     * @param Circles Array of circles in which the bounding radius is to be discovered.
     * @return Bounding radius of collection circles provided.
     */
    public static int CalculateBoundingRadius(Circle[] Circles) {
        ArrayList<Circle> SortedCircles = new ArrayList<>(Arrays.asList(Circles));
        SortedCircles.sort(Comparator.comparingInt(a -> a.GetPosition().Distance(0, 0) + a.GetRadius()));
        return SortedCircles.get(SortedCircles.size() - 1).GetPosition().Distance(0, 0)
                + SortedCircles.get(SortedCircles.size() - 1).GetRadius();
    }

}
