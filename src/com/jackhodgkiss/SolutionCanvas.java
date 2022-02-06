package com.jackhodgkiss;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class SolutionCanvas extends Solution {

    private int X;

    private int Y;

    private int Width;

    private int Height;

    private String Title;

    private int OriginX;

    private int OriginY;

    private PGraphics Canvas;

    private PApplet Child;


    /**
     * Construct a new instance of the Solution with the supplied solution set.
     * @param Circles Array of circles that form a valid solution.
     */
    public SolutionCanvas(Circle[] Circles, int X, int Y, int Width, int Height, String Title, PApplet Parent) {
        super(Circles);
        this.X = X;
        this.Y = Y;
        this.Width = Width;
        this.Height = Height;
        this.Title = Title;
        this.OriginX = this.Width / 2;
        this.OriginY = this.Height / 2;
        this.Canvas = Parent.createGraphics(Width, Height);
    }

    public void Update(PApplet Parent) {
        int MouseX = Parent.mouseX;
        int MouseY = Parent.mouseY;
        if(this.X < MouseX && this.X + this.Width > MouseX
                && this.Y < MouseY && this.Y + this.Height > MouseY) {
            if(Parent.mousePressed) {
                if(Parent.mouseButton == PConstants.LEFT) {
                    if(this.Child == null) {
                        String[] Args = new String[this.Circles.length + 1];
                        for(int i  = 0; i < Args.length - 1; i++) {
                            Args[i] = Integer.toString(this.Circles[i].GetRadius());
                        }
                        Args[Args.length - 1] = this.Title;
                        this.Child = new SolutionViewer(Args);
                    } else {
                        if(!this.Child.frame.isVisible()) {
                            this.Child = null;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void Draw(PApplet Parent) {
        this.Canvas.beginDraw();
        this.Canvas.clear();
        this.Canvas.pushMatrix();
        this.Canvas.translate(this.OriginX, this.OriginY);
        this.BoundaryCircle.Draw(this.Canvas);
        for(Circle C : Circles) {
            C.Draw(this.Canvas);
        }
        this.Canvas.popMatrix();
        this.Canvas.endDraw();
        Parent.image(this.Canvas, this.X, this.Y);
        Parent.noFill();
        Parent.rect(this.X, this.Y, this.Width, this.Height);
    }

    public int GetX() {
        return this.X;
    }

    public int GetY() {
        return this.Y;
    }

    public int GetWidth() {
        return this.Width;
    }

    public int GetHeight() {
        return this.Height;
    }
}
