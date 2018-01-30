/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.ga_uflp;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
public class MainGaUflp {

    final static long startTime = System.currentTimeMillis();
    static ActionFiles saveFile, saveSeed, creatD;
    static Scanner in = new Scanner(System.in);
    static ActionFiles file = new ActionFiles();
    static Locations location = new Locations();
    static ArrayList<String> inputFileCap = null;
    static ArrayList<Double> inputFileConfigCap = null;
    static ArrayList<Integer> inputFileSeed = null;
    static Properties textXml = new Properties();
    static ActionUFLP uflp = new ActionUFLP();
    ;
    static TestUFLP tUFLP = new TestUFLP();

    public static void main(String[] args) throws IOException {
        //location.getLocations();  ///สร้างตำแหน่งของสถานีกับลูกค้า

        textXml.loadFromXML(uflp.getXML()); //โหลดไฟล์xmlที่ไว้เก็บstring

        ArrayList<String> pathFiles = new ArrayList<>();
        String directoryName = "D:\\test\\Istanze";
        file.listPathFile(directoryName, pathFiles);
        System.out.println(pathFiles);
       

        for (int f = 0; f < 1; f++) {//(pathFiles.size() - 1)
             double minFinessGenAll = Double.MAX_VALUE;
            int[] minChromosomeAll = null ;
            uflp = new ActionUFLP(); //resetค่า
            MainGaUflp mc = new MainGaUflp();
            String nameFile = "";
            String directoryCap = "";
            String addressFolder = "D:\\test\\file";
            try {
                String pathFileCap = "D:\\test\\Istanze\\capa.txt";//pathFiles.get(f);
                mc.inputFileCap = file.readFile(pathFileCap);//D:\\test\\2\\3\\DistanceFile.txt///"D:\\DistanceFile.txt"//"D:\\cap71.txt"
                nameFile = pathFileCap.substring(pathFileCap.lastIndexOf("\\") + 1, pathFileCap.length());
                nameFile = nameFile.substring(0, nameFile.indexOf("."));
                creatD = new ActionFiles();
                creatD.creatingDirectory(addressFolder+"\\seed");  
                creatD.creatingDirectory(addressFolder+"\\summarize");  
                //saveSeed = new ActionFiles(addressFolder + "\\seed\\seed_" + nameFile + ".txt");  //สร้างไฟล์เก็บseed
                directoryCap = addressFolder + "\\" + nameFile;  //ที่อยู่โฟลเดอร์ที่จะสร้างไว้เก็บtxtแต่ละcap
                creatD.creatingDirectory(directoryCap);         //สร้างโฟลเดอร์
                mc.inputFileConfigCap = file.readFileDouble(addressFolder + "\\config\\config_" + nameFile + ".txt");//อ่านไฟล์ config แต่ละ cap
            } catch (Exception e) {
                System.err.println(textXml.getProperty("email.support"));
            }

            uflp.inputAll(mc.inputFileCap, textXml); //แสดงข้อมูล ระยะทางสถานีไปโกดัง ต้นทุนสถานี

            int chromosomeSize = uflp.getM();
            int populationSize = uflp.getS();//จำนวนindiv
            if (uflp.getS() % 2 != 0) {  //ถ้า s เป็นเลขคี่ จึง -1
                populationSize -= 1;
            }
            int index = 0;
            int numGenerations = inputFileConfigCap.get(index++).intValue();//จำนวนรุ่น
            double popCrossover = inputFileConfigCap.get(index++);//ความน่าจะเป็นในCrossover
            double popMutation = inputFileConfigCap.get(index++);//ความน่าจะเป็นในการผ่าเหล่า
            int numFile = inputFileConfigCap.get(index++).intValue();//จำนวนไฟล์ที่จะสร้าง

            double meanBestFitness = 0;//ค่าเฉลียฟิสเนส
            double standardDeviation = 0;//ค่าเบียนเบน
            double sumXPowTwo = 0; //ไว้หาส่วนเบียนเบน
            double sumX = 0;//ผลรวมของx

            for (int loopFile = 1; loopFile <= numFile; loopFile++) {
                mc.inputFileSeed = file.readFileInt(addressFolder + "\\seed\\seed_" + nameFile + ".txt");
                saveFile = new ActionFiles(directoryCap + "\\outputEachLoop_" + nameFile + "_" + loopFile + ".txt");
                int seed = (int) System.currentTimeMillis();
   //             saveSeed.write(String.valueOf(seed));
  //              saveSeed.nextLine();
                GA ga = new GA(chromosomeSize, populationSize, popCrossover, popMutation, inputFileSeed.get(9));//inputFileSeed.get(loopFile-1));//ส่uเวลาเพื่อตั้งให้seedเป็นไปตามรอบเวลาfile
                Map<String, Chromosome> mapParent = new TreeMap<>(); //map ของพ่อแม่
                Map<String, Chromosome> mapOffspring = new TreeMap<>();  //map ของลูกคนใหม่
                System.out.println(textXml.getProperty("ga.generation.number") + "0");
                saveFile.write(textXml.getProperty("ga.generation.number") + "0");
                saveFile.nextLine();
                //parent
                System.out.println(textXml.getProperty("ga.crossover.subject.parent"));//--Crossover Parent--

                mapParent = ga.createTableParent(ga.createTable(mapParent, uflp, textXml, tUFLP)); //สร้างตาราง chromosome ของพ่อแม่
                ga.printTableChromosomeAll(mapParent, saveFile); //แสดงตาราง พ่อแม่

                for (int gen = 1; gen <= numGenerations; gen++) {

                    String[] matingPool = ga.createMatingPool(mapParent); //สร้าง invividual และ matingPool
                    //offspring
                    System.out.println(textXml.getProperty("ga.crossover.subject.offspring"));//--Crossover Offspring--

                    mapOffspring = ga.crossover(mapParent, mapOffspring); //crossover พ่อแม่ กลายเป็น chromosome ลูก
                    //ga.sumFitnessOffspring(mapOff3spring,mapParent,tUFLP);
                    ga.sumFitnessOffspring(false, mapOffspring, mapParent, uflp, textXml, tUFLP);//หาผมลรวม Fitness ของลูก
                    //ga.printTableChromosome(mapOffspring);//แสดงตาราง

                    System.out.println(textXml.getProperty("ga.mutation.subject.offspring"));//"--One Point Mutation offspring--"

                    ga.onePointMutation(mapOffspring); //ผ่าเหล่าลูก
                    ga.sumFitnessOffspring(true, mapOffspring, mapParent, uflp, textXml, tUFLP);//หาผมลรวม Fitness ของลูก
                    mapParent = ga.createTableParent(mapOffspring); //สร้างตารางของ ลูก และเป็นตารางของพ่อแม่ต่อรุ่นต่อไป
                    saveFile.nextLine();
                    System.out.println(textXml.getProperty("ga.generation.number") + gen); //พิม Generation ปัจจุบัน
                    saveFile.write(textXml.getProperty("ga.generation.number") + gen);

                    ga.printTableChromosomeAll(mapOffspring, saveFile); //แสดงตาราง ลูก

                    System.out.println(textXml.getProperty("ga.generation.min.finess") + gen + " : " + ga.getMinFitness() + " " + ga.getMinCh()); //ค่า finess ที่น้อยที่สุด ในแต่ละรุ่น
                    saveFile.write(textXml.getProperty("ga.generation.min.finess") + gen + " : " + ga.getMinFitness() + " " + ga.getMinCh());
                    saveFile.nextLine();
                    System.out.println(textXml.getProperty("ga.generation.min.chromosome") + gen + " : " + Arrays.toString(ga.getMinChromosome())); //chromosomeที่ให้ค้่finessที่น้อยที่สุดในแต่ละรุ่น
                    saveFile.write(textXml.getProperty("ga.generation.min.chromosome") + gen + " : " + Arrays.toString(ga.getMinChromosome()));
                    saveFile.nextLine();

                    System.out.println(textXml.getProperty("ga.generation.best.finess") + ga.getBestFitness());  //finessที่น้อยที่สุด
                    saveFile.write(textXml.getProperty("ga.generation.best.finess") + ga.getBestFitness());
                    saveFile.nextLine();
                    System.out.println(textXml.getProperty("ga.generation.best.chromosome") + Arrays.toString(ga.getBestChromosome()));
                    saveFile.write(textXml.getProperty("ga.generation.best.chromosome") + Arrays.toString(ga.getBestChromosome()));
                    saveFile.nextLine();
                    System.out.println("______________________________");

                }
                saveFile.close();
                saveFile = new ActionFiles(directoryCap + "\\outputFinal_" + nameFile + "_" + loopFile + ".txt");

                saveFile.write(textXml.getProperty("ga.generation.best.finess") + ga.getBestFitness());
                saveFile.nextLine();
                saveFile.write(textXml.getProperty("ga.generation.best.chromosome") + Arrays.toString(ga.getBestChromosome()));
                saveFile.nextLine();

                saveFile.close();

                ///หาค่าเฉลียของไฟล์ที่ได้มา
                sumX = sumX + ga.getBestFitness();

                sumXPowTwo = sumXPowTwo + Math.pow(ga.getBestFitness(), 2);
                //standardDeviation  += Math.pow(meanBestFitness+ga.getBestFitness() - meanBestFitness , 2);
               if( ga.getBestFitness()<minFinessGenAll){
                    minFinessGenAll =  ga.getBestFitness();
                    minChromosomeAll=ga.getBestChromosome();
                }

            }
            saveFile = new ActionFiles(addressFolder + "\\summarize\\summarize_" + nameFile + ".txt");
            //เฉลีย
            meanBestFitness = sumX / numFile;
            System.out.println("meanBestFitness: " + meanBestFitness);
            saveFile.write(textXml.getProperty("meanBestFitness") + meanBestFitness);
            saveFile.nextLine();
            //เบียนเบน
            standardDeviation = (numFile * sumXPowTwo) - (Math.pow(sumX, 2));
            System.out.println("standardDeviation: " + standardDeviation);

            saveFile.write(textXml.getProperty("standardDeviation") + standardDeviation);
            saveFile.nextLine();
            saveFile.close();
            //saveSeed.close();
            saveFile = new ActionFiles(addressFolder + "\\min_all_"+ nameFile + ".txt");
            saveFile.write(String.valueOf(minFinessGenAll));
            saveFile.nextLine();
            saveFile.write(Arrays.toString(minChromosomeAll));
            saveFile.close();
            
             

        }
//time
        double milliseconds = System.currentTimeMillis() - startTime;
        double seconds = (milliseconds / 1000) % 60;
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
        System.out.println("Total time [hours.seconds.milliseconds]: "+hours+"."+ seconds);
    }

}
