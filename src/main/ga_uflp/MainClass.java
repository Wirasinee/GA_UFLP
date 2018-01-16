/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.ga_uflp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;
import pGa.*;
import pUflp.*;

/**
 *
 * @author Wirasinee
 */
public class MainClass {

    static Scanner in = new Scanner(System.in);
    static ActionFiles file = new ActionFiles();
    static Locations location = new Locations();
    static ArrayList<Double> inputFile = null;
    static Properties textXml = new Properties();
    static ActionUFLP uflp = new ActionUFLP();
    static TestUFLP tUFLP = new TestUFLP();

    public static void main(String[] args) throws IOException {
        location.getLocations();  ///สร้างตำแหน่งของสถานีกับลูกค้า
        MainClass mc = new MainClass();
        textXml.loadFromXML(uflp.getXML()); //โหลดไฟล์xmlที่ไว้เก็บstring
        try {
            mc.inputFile = file.readFile("D:\\DistanceFile.txt");//D:\\test\\2\\3\\DistanceFile.txt
        } catch (Exception e) {
            System.err.println(textXml.getProperty("email.support"));
        }
        uflp.inputAll(mc.inputFile, textXml); //แสดงข้อมูล ระยะทางสถานีไปโกดัง ต้นทุนสถานี
        
        int chromosomeSize = uflp.getN();
        int populationSize = uflp.getS()* uflp.getS();  //populationSizeเป็น2เท่า
        if (uflp.getS() % 2 != 0) {  //ถ้า s เป็นเลขคี่พอเป็น2เท่าจะได้เลขคี่ จึง -1
            populationSize -= 1; 
        }

        int numGenerations = 30;//จำนวนรุ่น
        double popCrossover = 0.75;//ความน่าจะเป็นในCrossover
        double popMutation = 0.2;//ความน่าจะเป็นในการผ่าเหล่า
        GA ga = new GA(chromosomeSize, populationSize, popCrossover, popMutation);
        Map<String, Chromosome> mapParent = new TreeMap<>(); //map ของพ่อแม่
        Map<String, Chromosome> mapOffspring = new TreeMap<>();  //map ของลูกคนใหม่
        System.out.println(textXml.getProperty("ga.generation.number")+"0");
        //parent
        System.out.println(textXml.getProperty("ga.crossover.subject.parent"));//--Crossover Parent--
        mapParent = ga.createTableParent(ga.createTable(mapParent, uflp, textXml, tUFLP)); //สร้างตาราง chromosome ของพ่อแม่
        ga.printTableChromosomeAll(mapParent); //แสดงตาราง พ่อแม่

        for (int gen = 1; gen <= numGenerations; gen++) {

            String[] matingPool = ga.createMatingPool(mapParent); //สร้าง invividual และ matingPool
            //offspring
            System.out.println(textXml.getProperty("ga.crossover.subject.offspring"));//--Crossover Offspring--
            mapOffspring = ga.crossover(mapParent, mapOffspring); //crossover พ่อแม่ กลายเป็น chromosome ลูก
            //ga.sumFitnessOffspring(mapOff3spring,mapParent,tUFLP);
            ga.sumFitnessOffspring(false,mapOffspring, mapParent, uflp, textXml, tUFLP);//หาผมลรวม Fitness ของลูก
            ga.printTableChromosome(mapOffspring);//แสดงตาราง
            
            System.out.println(textXml.getProperty("ga.mutation.subject.offspring"));//"--One Point Mutation offspring--"
            ga.onePointMutation(mapOffspring); //ผ่าเหล่าลูก
            ga.sumFitnessOffspring(true,mapOffspring, mapParent, uflp, textXml, tUFLP);//หาผมลรวม Fitness ของลูก
            mapParent = ga.createTableParent(mapOffspring); //สร้างตารางของ ลูก และเป็นตารางของพ่อแม่ต่อรุ่นต่อไป

            System.out.println(textXml.getProperty("ga.generation.number")+ gen); //พิม Generation ปัจจุบัน
            ga.printTableChromosomeAll(mapOffspring); //แสดงตาราง ลูก
            
            System.out.println(textXml.getProperty("ga.generation.min.finess")+gen+" : "+ga.getMinFitness()+" "+ga.getMinCh()); //ค่า finess ที่น้อยที่สุด ในแต่ละรุ่น
            System.out.println(textXml.getProperty("ga.generation.min.chromosome")+gen+" : "+Arrays.toString(ga.getMinChromosome())); //chromosomeที่ให้ค้่finessที่น้อยที่สุดในแต่ละรุ่น
            
            System.out.println(textXml.getProperty("ga.generation.best.finess")+ga.getBestFitness());  //finessที่น้อยที่สุด
            System.out.println(textXml.getProperty("ga.generation.best.chromosome")+Arrays.toString(ga.getBestChromosome()));  
            System.out.println("______________________________");

        }

    }

}
