package com.jackhodgkiss;

import processing.core.PApplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends PApplet {

    private int[] Radii = {10, 34, 10, 55, 30, 14, 70, 14};

    private int PopulationSize = 10;

    private float MutationRate = 0.01f;

    private int CrossoverLength = this.Radii.length / 3;

    private int GenerationLimit = 50;

    private Explorer A;

    private File Configuration;

    private Scanner In;

    @Override
    public void settings() {
        this.size(1280, 720);
        this.smooth(8);
    }

    @Override
    public void setup() {
        this.surface.setTitle("Software Agents & Optimisations | Jack Hodgkiss - 14036299");
        this.background(255);
        this.Configuration = new File("Configuration.txt");
        try {
            this.In = new Scanner(this.Configuration);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }
        if(this.In != null) {
            while(this.In.useDelimiter("\n").hasNext()) {
                String Line =  this.In.useDelimiter("\n").next().replace("\r", "");
                String[] Elements = Line.split(" ");
                for(int i = 0; i < Elements.length; i++) {
                    if(Elements[i].equals("-radii")) {
                        int n = Integer.parseInt(Elements[i + 1]);
                        Radii = new int[n];
                        for(int j = 0; j < n; j++) {
                            Radii[j] = Integer.parseInt(Elements[(j + i) + 2]);
                        }
                        this.CrossoverLength = this.Radii.length / 3;
                    } else if(Elements[i].equals("-population")) {
                        this.PopulationSize = Integer.parseInt(Elements[i + 1]);
                    } else if(Elements[i].equals("-mutation")) {
                        this.MutationRate = Float.parseFloat(Elements[i + 1]);
                    } else if(Elements[i].equals("-crossover")) {
                        this.CrossoverLength = Integer.parseInt(Elements[i + 1])
                                < this.Radii.length ? Integer.parseInt(Elements[i + 1]) : this.Radii.length / 3;
                    } else if(Elements[i].equals("-generation")) {
                        this.GenerationLimit = Integer.parseInt(Elements[i + 1]);
                    }
                }
            }
        }
        if(args != null) {
        	for(int i = 0; i < args.length; i++) {
                if(args[i].equals("-radii")) {
                    int n = Integer.parseInt(args[i + 1]);
                    Radii = new int[n];
                    for(int j = 0; j < n; j++) {
                        Radii[j] = Integer.parseInt(args[(j + i) + 2]);
                    }
                    this.CrossoverLength = this.Radii.length / 3;
                } else if(args[i].equals("-population")) {
                    this.PopulationSize = Integer.parseInt(args[i + 1]);
                } else if(args[i].equals("-mutation")) {
                    this.MutationRate = Float.parseFloat(args[i + 1]);
                } else if(args[i].equals("-crossover")) {
                    this.CrossoverLength = Integer.parseInt(args[i + 1])
                            < this.Radii.length ? Integer.parseInt(args[i + 1]) : this.Radii.length / 3;
                } else if(args[i].equals("-generation")) {
                    this.GenerationLimit = Integer.parseInt(args[i + 1]);
                }
            }
        }
        A = new Explorer(this.Radii, this.PopulationSize, this.MutationRate,
                this.CrossoverLength, this.GenerationLimit, this);
    }

    private void Update() {
        A.Update();
    }

    @Override
    public void draw() {
        this.Update();
        this.background(255);
        A.Draw();
    }

    /**
     * Main entry point of the application.
     * @param args String array of command line arguments provided.
     */
    public static void main(String[] args) {
        PApplet.main("com.jackhodgkiss.Main", args);
    }

}
