package com.jackhodgkiss;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for logging to a csv file the progress of the genetic algorithm running within the
 * program.
 */
public class Logger {

    private ArrayList<Solution> Data;

    private Path File;

    private int[] Radii;

    private int Population;

    private float Mutation;

    private int Crossover;

    public Logger(int[] Radii, int Population, float Mutation, int Crossover) {
        this.Data = new ArrayList<>();
        String URL = (new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date())) + ".csv";
        File = Paths.get(URL);
        this.Radii = Radii;
        this.Population = Population;
        this.Mutation = Mutation;
        this.Crossover = Crossover;
    }

    public void AppendBuffer(ArrayList<Solution> More) {
        Data.addAll(More);
    }

    public void Write() {
        List<String> Headers = Arrays.asList("Genome,Fitness,Radii,Population,Mutation,Crossover Length");
        ArrayList<String> Lines = new ArrayList<>(Headers);
        for(Solution S : this.Data) {
            int[] Genome = new int[S.Circles.length];
            for(int i = 0; i < Genome.length; i++) {
                Genome[i] = S.Circles[i].GetRadius();
            }
            Lines.add(String.format("%s, %d, %s, %d, %.3f, %d",
                    Arrays.toString(Genome).replace(",", ""), S.GetFitness(),
                    Arrays.toString(this.Radii).replace(",", ""),
                    this.Population, this.Mutation, this.Crossover));
        }
        try {
            Files.write(File, Lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
