package com.jackhodgkiss;

import sun.net.www.http.ChunkedInputStream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class will house the genetic algorithm used against the circle packing problem within this application.
 * Progression to the next generation will be made possible by calling the Solve function.
 */
public class GeneticSolver extends Solver {

    private DNA[] Population;

    private int PopulationSize;

    private double MutationRate;

    private int CrossoverLength;

    private int GenerationCount;

    private Random R;

    /**
     * Construct a new instance of GeneticSolver with the set of radii provided.
     * @param Radii Array of radii used by each circle within the problem to be solved.
     */
    public GeneticSolver(int[] Radii, int PopulationSize, double MutationRate, int CrossoverLength) {
        super(Radii);
        this.PopulationSize = PopulationSize;
        this.MutationRate = MutationRate;
        this.CrossoverLength = CrossoverLength;
        this.Population = new DNA[this.PopulationSize];
        this.R = new Random();
        this.InitialisePopulation();
    }

    /**
     * Initialise the population of the genetic solver by copying each radii from the supplied array. Shuffle the list
     * in order to introduce initial diversity and then construct new instance of DNA class with these shuffled radii.
     */
    private void InitialisePopulation() {
        ArrayList<Integer> ShuffledRadii =
                new ArrayList<>(Arrays.stream(this.Radii).boxed().collect(Collectors.toList()));
        for(int i = 0; i < this.PopulationSize; i++) {
            Collections.shuffle(ShuffledRadii);
            int[] Genes = new int[this.Radii.length];
            for(int j = 0; j < Genes.length; j++) {
                Genes[j] = ShuffledRadii.get(j);
            }
            this.Population[i] = new DNA(Genes, this.MutationRate, this.CrossoverLength);
        }
    }

    /**
     * This function shall solve the circle placement problem by running a genetic algorithm for one generation each call.
     * Each row corresponds to an individual solution to the current problem.
     * @return Array of circles. Each row refers to an individual solution to the current problem within the current
     * generation.
     */
    @Override
    public Circle[][] Solve() {
        GenerationCount++;
        Circle[][] Result = new Circle[this.PopulationSize][this.Radii.length];
        DNA[] Ranking = Population;
        ArrayList<DNA> MatingPool = new ArrayList<>();
        Arrays.asList(Ranking).sort(Comparator.comparingInt(DNA::GetFitness).reversed());
        for(int i = 0; i < Ranking.length; i++) {
            for(int j = 0; j < i; j++) {
                MatingPool.add(Ranking[i]);
            }
        }
        for(int i = 0; i < this.PopulationSize; i++) {
            DNA PartnerA = MatingPool.get(R.nextInt(MatingPool.size()));
            DNA PartnerB = MatingPool.get(R.nextInt(MatingPool.size()));
            DNA Child = PartnerA.Crossover(PartnerB);
            Child.Mutate();
            this.Population[i] = Child;
        }
        for(int Row = 0; Row < this.PopulationSize; Row++) {
            Circle[] Circles = this.Population[Row].GetCircles();
            for(int Col = 0; Col < this.Radii.length; Col++) {
                Result[Row][Col] = Circles[Col];
            }
        }
        return Result;
    }

    public int GetGenerationCount() {
        return this.GenerationCount;
    }
}
