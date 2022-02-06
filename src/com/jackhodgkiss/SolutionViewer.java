package com.jackhodgkiss;

import processing.core.PApplet;

public class SolutionViewer extends PApplet {

    private Solution Solution;

    private String Title;

    private int Fitness;

    public SolutionViewer(String[] args) {
        this.runSketch();
        this.args = args;
    }

    @Override
    public void settings() {
        this.size(720, 640);
        this.smooth(8);
    }

    @Override
    public void setup() {
        this.background(255);
        this.surface.setResizable(true);
        int[] Radii = new int[this.args.length - 1];
        for(int i = 0; i < this.args.length - 1; i++) {
            Radii[i] = Integer.parseInt(args[i]);
        }
        Circle[] Circles = Utility.PositionCircles(Radii);
        this.Solution = new Solution(Circles);
        this.Title = this.args[this.args.length - 1];
        this.surface.setTitle("Solution Viewer | " + Title);
        this.Fitness = this.Solution.GetFitness();
    }

    private void Update() {

    }

    @Override
    public void exit() {
        this.surface.setVisible(false);
        this.frame.dispose();
    }

    @Override
    public void draw() {
        this.Update();
        this.background(255);
        Solution.Draw(this);
        this.fill(0);
        this.textSize(18);
        this.text(this.Title, this.width / 2, (this.height / 2) - this.Fitness - 15);
        this.text(String.format("Boundary: %d", this.Fitness), this.width / 2, (this.height / 2) + this.Fitness + 15);
    }

}
