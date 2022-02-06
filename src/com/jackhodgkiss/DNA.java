package com.jackhodgkiss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class is responsible for representing a single expression of a solution to the active problem set within the
 * genetic algorithm.
 */
public class DNA {

    private int[] Genes;

    private Circle[] Circles;

    private double MutationRate;

    private int CrossoverLength;

    private Random R;

    /**
     * Construct a new instance of DNA with the supplied parameters.
     * @param Radii Array of radii that make up the problem.
     * @param MutationRate Rate at which the genes may mutate.
     * @param CrossoverLength Length of crossover region between two encodings.
     */
    public DNA(int[] Radii, double MutationRate, int CrossoverLength) {
        this.Genes = Radii;
        this.MutationRate = MutationRate;
        this.CrossoverLength = CrossoverLength;
        this.R = new Random();
        GenerateCircles();
    }

    public DNA Crossover(DNA Other) {
        int[] ChildGenes = new int[this.Genes.length];
        int[] OtherGenes = Other.GetGenes();
        int CrossoverStart  = R.nextInt(this.Genes.length);
        ArrayList<Integer> CrossoverIndexes = new ArrayList<>();
        ArrayList<Integer> GenesLeftToCopy =
                new ArrayList<>(Arrays.stream(OtherGenes).boxed().collect(Collectors.toList()));
        for(int i = CrossoverStart; i < CrossoverStart + CrossoverLength; i++) {
            CrossoverIndexes.add(i >= this.Genes.length ? i - this.Genes.length : i);
        }
        for(int index : CrossoverIndexes) {
            ChildGenes[index] = this.Genes[index];
            GenesLeftToCopy.remove(GenesLeftToCopy.indexOf(this.Genes[index]));
        }
        for(int j = 0; j < ChildGenes.length - CrossoverLength; j++) {
            int Cursor = CrossoverIndexes.get(CrossoverLength - 1) + (j + 1);
            Cursor = Cursor >= this.Genes.length ? Cursor - this.Genes.length : Cursor;
            if(GenesLeftToCopy.contains(OtherGenes[Cursor])) {
                ChildGenes[Cursor] = OtherGenes[Cursor];
                GenesLeftToCopy.remove(GenesLeftToCopy.indexOf(OtherGenes[Cursor]));
            } else {
                for(int k = 0; k < OtherGenes.length; k++) {
                    if(GenesLeftToCopy.contains(OtherGenes[k])) {
                        ChildGenes[Cursor] = OtherGenes[k];
                        GenesLeftToCopy.remove(GenesLeftToCopy.indexOf(OtherGenes[k]));
                        break;
                    }
                }
            }
        }
        return new DNA(ChildGenes, this.MutationRate, this.CrossoverLength);
    }

    /**
     * This function is responsible for applying some form mutation to the genes held within this DNA instance. It will
     * simply swap one value with its neighbour if RNG results to less than the applied mutation rate.
     */
    public void Mutate() {
        for(int i = 0; i < this.Genes.length; i++) {
            if(R.nextDouble() < this.MutationRate) {
                int GeneA = this.Genes[i];
                int GeneB = this.Genes[i == this.Genes.length - 1 ? 0 : i + 1];
                this.Genes[i] = GeneB;
                this.Genes[i == this.Genes.length - 1 ? 0 : i + 1] = GeneA;
            }
        }
        GenerateCircles();
    }

    /**
     * Must be called when ever the composition of genes change. This will generate a new array of circles with the
     * updated placement arrangement.
     */
    private void GenerateCircles() {
        this.Circles = Utility.PositionCircles(this.Genes);
    }

    public int[] GetGenes() {
        return this.Genes;
    }

    public Circle[] GetCircles() {
        return this.Circles;
    }

    public double GetMutationRate() {
        return this.MutationRate;
    }

    public int GetCrossoverLength() {
        return this.CrossoverLength;
    }

    /**
     * Get the fitness value of the expressed DNA.
     * @return Fitness value is the radius of the boundary circle. Smaller the fitness value the better.
     */
    public int GetFitness() { return Utility.CalculateBoundingRadius(this.Circles); }

    @Override
    public String toString() {
        return String.format("Genes: %s",Arrays.toString(this.Genes));
    }

}
