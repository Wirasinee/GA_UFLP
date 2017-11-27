package pGa;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;
import pUflp.ActionUFLP;
import pUflp.TestUFLP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wirasinee
 */
public class Chromosome {

    private int[] chromosome;
    private int value;
    private double fitness;
    private double percentage;
    private double sumPercentage;

    public Chromosome(int[] chr) {
        setChromosome(chr);
    }

    public Chromosome(int[] chromosome, int value, double fitness, double percentage, double sumPercentage) {
        this.chromosome = chromosome;
        this.value = value;
        this.fitness = fitness;
        this.percentage = percentage;
        this.sumPercentage = sumPercentage;
    }

   

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        final DecimalFormat FormatPercentage = new DecimalFormat("0.####");
        this.fitness = Double.parseDouble(FormatPercentage.format(fitness));
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getSumPercentage() {
        return sumPercentage;
    }

    public void setSumPercentage(double sumPercentage) {
        this.sumPercentage = sumPercentage;
    }

    void setFitness(int[] chromosome, String dec, Map<String, Chromosome> mapOldChromosome, ActionUFLP uflp, Properties textXml, TestUFLP tUFLP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
