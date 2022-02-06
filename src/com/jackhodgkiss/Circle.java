package com.jackhodgkiss;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * This class represents a circle within the application. This class can hold all of the information to draw a circle
 * within the application when needed. Radius below 8 will cause difficulties in reading the label due to the font size
 * of being half of the radius. However there are no restrictions on the radius.
 */
public class Circle {

    private int Radius;

    private Position2D Position;

    private String Label;

    private boolean IsLabelVisible;

    /**
     * Construct a new instance of circle with the supplied parameters.
     * @param Radius The radius of the circle half the diameter.
     * @param X Location in which to place the circle within the x-axis.
     * @param Y Location in which to place the circle within the y-axis.
     * @param IsLabelVisible Boolean to determine if the label is be drawn on to the circle.
     */
    public Circle(int Radius, int X, int Y, boolean IsLabelVisible) {
        this.Radius = Radius;
        this.Position = new Position2D(X, Y);
        this.Label = Integer.toString(this.Radius);
        this.IsLabelVisible = IsLabelVisible;
    }

    /**
     * Construct a new instance of circle with the supplied parameters.
     * @param Radius The radius of the circle half the diameter.
     * @param Position The position were the circle shall be drawn.
     * @param IsLabelVisible Boolean to determine if the label is be drawn on to the circle.
     */
    public Circle(int Radius, Position2D Position, boolean IsLabelVisible) {
        this.Radius = Radius;
        this.Position = Position;
        this.Label = Integer.toString(this.Radius);
        this.IsLabelVisible = IsLabelVisible;
    }

    /**
     * Draw the circle with its radius and at its position. The radius is drawn on top of the circle to act as label.
     * @param Parent PApplet instance required to access the drawing capabilities of processing.
     */
    public void Draw(PApplet Parent) {
        Parent.stroke(0);
        Parent.fill(255);
        Parent.ellipseMode(PConstants.CENTER);
        Parent.ellipse(this.Position.GetX(), this.Position.GetY(), Radius * 2, Radius * 2);
        Parent.ellipseMode(PConstants.CENTER);
        if(this.IsLabelVisible) {
            Parent.fill(0);
            Parent.textSize(Radius / 2 < 8 ? 8 : Radius / 2);
            Parent.textAlign(PConstants.CENTER, PConstants.CENTER);
            Parent.text(Label, this.Position.GetX(), this.Position.GetY());
        }
    }

    public void Draw(PGraphics Parent) {
        Parent.stroke(0);
        Parent.fill(255);
        Parent.ellipseMode(PConstants.CENTER);
        Parent.ellipse(this.Position.GetX(), this.Position.GetY(), Radius * 2, Radius * 2);
        Parent.ellipseMode(PConstants.CENTER);
        if(this.IsLabelVisible) {
            Parent.fill(0);
            Parent.textSize(Radius / 2 < 8 ? 8 : Radius / 2);
            Parent.textAlign(PConstants.CENTER, PConstants.CENTER);
            Parent.text(Label, this.Position.GetX(), this.Position.GetY());
        }
    }

    public int GetRadius() {
        return this.Radius;
    }

    public Position2D GetPosition() {
        return this.Position;
    }

    public String GetLabel() {
        return this.Label;
    }

    @Override
    public String toString() {
        return String.format("Radius: %d X: %d Y: %d Label: %s",
                this.Radius, this.Position.GetX(), this.Position.GetY(), this.Label);
    }
}
