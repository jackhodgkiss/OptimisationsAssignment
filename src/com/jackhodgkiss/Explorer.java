package com.jackhodgkiss;

import com.sun.org.apache.bcel.internal.generic.POP;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.Comparator;

public class Explorer {

    private int[] Radii;

    private PApplet Parent;

    private Solver[] Solvers;

    private SolutionCanvas[] Solutions;

    private Circle[] BestGASolution;

    private int PopulationSize;

    private float MutationRate;

    private int CrossoverLength;

    private int GenerationLimit;

    private Logger GAPerformanceLogger;

    private boolean Running = true;

    public Explorer(int[] Radii, int PopulationSize, float MutationRate,
                    int CrossoverLength, int GenerationLimit, PApplet Parent) {
        this.Radii = Radii;
        this.PopulationSize = PopulationSize;
        this.MutationRate = MutationRate;
        this.CrossoverLength = CrossoverLength;
        this.GenerationLimit = GenerationLimit;
        this.Parent = Parent;
        this.Initialise();
    }

    private void Initialise() {
        this.Solvers = new Solver[3];
        for(int i = 0; i < this.Solvers.length; i++) {
            Solver S;
            if(i == 0)
                S = new OrderedSolver(this.Radii);
            else if(i == 1)
                S = new GreedySolver(this.Radii);
            else
                S = new GeneticSolver(this.Radii, PopulationSize, MutationRate, CrossoverLength);
            this.Solvers[i] = S;
        }
        ArrayList<Solution> GeneticSolutionsSorted = new ArrayList<Solution>();
        Circle[][] GeneticSolutions = this.Solvers[2].Solve();
        for(int i = 0; i < GeneticSolutions.length; i++) {
            GeneticSolutionsSorted.add(new Solution(GeneticSolutions[i]));
        }
        GeneticSolutionsSorted.sort(Comparator.comparingInt(Solution::GetFitness));
        this.Solutions = new SolutionCanvas[3];
        for(int i = 0; i < this.Solutions.length; i++) {
            SolutionCanvas S;
            if(i == 0)
                S = new SolutionCanvas(this.Solvers[i].Solve()[0], 50 + (i * 400),
                        50, 350, 250, "Ordered Placement", this.Parent);
            else if(i == 1)
                S = new SolutionCanvas(this.Solvers[i].Solve()[0], 50 + (i * 400),
                        50, 350, 250, "Greedy Algorithm", this.Parent);
            else
                S = new SolutionCanvas(GeneticSolutionsSorted.get(0).Circles, 50 + (i * 400),
                        50, 350, 250, "Genetic Algorithm", this.Parent);
            this.Solutions[i] = S;
        }
        this.GAPerformanceLogger = new Logger(this.Radii, this.PopulationSize, this.MutationRate, this.CrossoverLength);
    }

    public void Update() {
        for(SolutionCanvas SC : this.Solutions) {
            SC.Update(this.Parent);
        }
        GeneticSolver GS = (GeneticSolver)this.Solvers[2];
        if(GS.GetGenerationCount() < this.GenerationLimit) {
            ArrayList<Solution> GeneticSolutionsSorted = new ArrayList<Solution>();
            Circle[][] GeneticSolutions = GS.Solve();
            for(int i = 0; i < GeneticSolutions.length; i++) {
                GeneticSolutionsSorted.add(new Solution(GeneticSolutions[i]));
            }
            GeneticSolutionsSorted.sort(Comparator.comparingInt(Solution::GetFitness));
            Solutions[2].SetCircles(GeneticSolutionsSorted.get(0).Circles);
            this.BestGASolution = this.BestGASolution == null ? GeneticSolutionsSorted.get(0).Circles :
                    Utility.CalculateBoundingRadius(this.BestGASolution)
                            > Utility.CalculateBoundingRadius(GeneticSolutionsSorted.get(0).Circles)
                            ? GeneticSolutionsSorted.get(0).Circles : this.BestGASolution;
            this.GAPerformanceLogger.AppendBuffer(GeneticSolutionsSorted);
        } else {
            if(Running) {
                Solutions[2].SetCircles(this.BestGASolution);
                this.GAPerformanceLogger.Write();
                this.Running = false;
            }
        }
    }

    public void Draw() {
        for(int i = 0; i < this.Solutions.length; i++) {
            SolutionCanvas SC = this.Solutions[i];
            SC.Draw(this.Parent);
            Parent.fill(0);
            Parent.textSize(18);
            this.Parent.textAlign(PConstants.CENTER);
            if(i != 2) {
                this.Parent.text(String.format("Boundary: %d", SC.GetFitness()),
                        SC.GetX() + SC.GetWidth() / 2, SC.GetY() + (SC.GetHeight() + 25));
            } else {
                GeneticSolver GS = (GeneticSolver)Solvers[i];
                this.Parent.text(String.format("Boundary: %d, Generation: %d",
                        SC.GetFitness(), GS.GetGenerationCount()),
                        SC.GetX() + SC.GetWidth() / 2, SC.GetY() + (SC.GetHeight() + 25));
            }
            this.Parent.text(i == 0 ? "Ordered Placement" : i == 1 ? "Greedy Algorithm" : "Genetic Algorithm",
                    SC.GetX() + SC.GetWidth() / 2, SC.GetY() - 15);
            this.Parent.text("Mutation Rate: " + this.MutationRate, 1025, 375);
            this.Parent.text("Population: " + this.PopulationSize, 1025, 400);
            this.Parent.text("Crossover Length: " + this.CrossoverLength, 1025, 425);
            this.Parent.text("Generation Limit: " + this.GenerationLimit, 1025, 450);
            this.Parent.text("Best Radius: " + Utility.CalculateBoundingRadius(this.BestGASolution),  1025, 475);

        }
    }


}
