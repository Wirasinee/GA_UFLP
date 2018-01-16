package pGa;

import java.io.IOException;
import pGa.Chromosome;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import static pGa.GA.textXml;
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
public class GA {
    static Properties textXml = new Properties();
    static ActionUFLP uflp = new ActionUFLP();
    private double minFitness ,bestFitness = Double.MAX_VALUE; // minFitness เก็บ Fitness ที่ดีที่สุดในแต่ละรุ่น , bestFitness เก็บ Fitness สุดท้ายที่ดีที่สุด
    private String  minCh; //เก็บch ที่ให้Fitnessที่ดีที่สุดในแต่ละรุ่น
    private int[] minChromosome, bestChromosome;  // minChromosome เก็บChromosome ที่ให้ค่า Fitness ที่ดีที่สุดในแต่ละรุ่น , bestChromosome เก็บChromosome ที่ให้ค่า Fitness สุดท้ายที่ดีที่สุด
    ActionGA actionGA = new ActionGA();
    private int chromosomeSize;
    private int populationSize;
    final DecimalFormat FormatPercentage = new DecimalFormat("0.####");//ฟอแมตเลขทศนิยม
    private double sumFitness;               //ผลรวม Fitness
    private double sumPercentage = 0;       //ผลรวม %
    private int[] indiv;
    private final double popCrossover;     //ความน่าจะเป็นในCrossover
    private final double popMutation;      //ความน่าจะเป็นในการผ่าเหล่า
    private double numRandom;               //เก็บเลขที่สุ่มมาได้
    private String[] matingPool;
    Scanner in = new Scanner(System.in);

    public GA(int chromosomeSize, int populationSize, double popCrossover, double popMutation) throws IOException {
        this.chromosomeSize = chromosomeSize;
        this.populationSize = populationSize;
        this.indiv = new int[chromosomeSize];
        this.matingPool = new String[populationSize];
        this.popCrossover = popCrossover;
        this.popMutation = popMutation;
        textXml.loadFromXML(uflp.getXML()); //โหลดไฟล์xmlที่ไว้เก็บstring
    }


    public Map<String, Chromosome> createTable(Map<String, Chromosome> mapChromosome, ActionUFLP uflp, Properties textXml, TestUFLP tUFLP) {

        for (int q = 1; q <= populationSize; q++) {
            /*uflp.getN()=chromosomeSize*/
            // String dec = "";
            //String openString = String.format(("%" + chromosomeSize + "s"), Integer.toBinaryString(q)).replace(' ', '0');//แปลงเลขเช่น1=>001 ความหมายคือเปิดโกดัง3
            int value1 = 0;     //ไว้นับว่ามีเลข1กี่ตัวใน[] เช่น[0,1,1] => 2ตัว
            int value0 = 0;     //ไว้นับว่ามีเลข0กี่ตัวใน[] เช่น[0,1,1] => 1ตัว

            for (int j = 0; j < chromosomeSize; j++) {//3

                //indiv[j] = (Integer.parseInt(openString.charAt(j) + ""));//ความเป็นไปได้ของโกดังที่เปิด
                indiv[j] = actionGA.randomZeroOrOne();

                if (indiv[j] == 1) { //ถ้ามีเลข1ก็นับไว้
                    value1++;
                } else {
                    value0++;
                }
                if (value0 == chromosomeSize) { //ถ้าเป็น[0,0,0] ควรที่จะสุ่มใหม่
                    int x = actionGA.RandomTo(1, chromosomeSize);

                    //indiv[j] = (Integer.parseInt(openString.charAt(j) + ""));//ความเป็นไปได้ของโกดังที่เปิด
                    for (int randomIndexOne = 0; randomIndexOne < x; randomIndexOne++) {
                        int indexOne = actionGA.RandomTo(0, chromosomeSize);
                        indiv[indexOne] = 1;
                        value1++;
                    }

                    //System.out.println(q + "  x:" + x + "v=c" + value0 + "=" + chromosomeSize);

                }
            }

            Chromosome c = new Chromosome(indiv.clone());
            //int value = Integer.parseInt(dec, 2);       //แปลงเป็นเลขฐาน10
            c.setValue(value1);
            c.setFitness(tUFLP.mainTestold(indiv, uflp, textXml));       //หาค่าFitness
            sumFitness += c.getFitness();               //+เก็บค่าผลรวมของFitness

            mapChromosome.put(textXml.getProperty("ch") + (q), c);

        }
        //System.out.println("sumFitness:" + sumFitness);
        return mapChromosome;
    }

    public Map<String, Chromosome> createTableParent(Map<String, Chromosome> mapChromosome) {
        sumPercentage = 0;
        for (int i = 1; i <= populationSize; i++) {
            String key = textXml.getProperty("ch") + i;
            double percentage = (mapChromosome.get(key).getFitness() * 100) / (double) sumFitness;
            percentage = Double.parseDouble(FormatPercentage.format(percentage));
            sumPercentage = Double.parseDouble(FormatPercentage.format((sumPercentage + percentage)));
            mapChromosome.get(key).setPercentage(percentage);
            mapChromosome.get(key).setSumPercentage(sumPercentage);
        }
        return mapChromosome;
    }

    public void printTableChromosome(Map<String, Chromosome> mapChromosome){
        System.out.println(textXml.getProperty("ga.chromosome.table"));//"Table Chromosome (indiv|chromosome|value|fitness)"
        for (int i = 1; i <= mapChromosome.size(); i++) {
            String key = textXml.getProperty("ch") + i;
            System.out.println(key + "  " + Arrays.toString(mapChromosome.get(key).getChromosome()) + "  " + mapChromosome.get(key).getValue()
                    + "   " + mapChromosome.get(key).getFitness());
        }
    }

    public void printTableChromosomeAll(Map<String, Chromosome> mapChromosome){
        System.out.println(textXml.getProperty("ga.chromosome.table.full"));
        for (int i = 1; i <= mapChromosome.size(); i++) {
            String key = textXml.getProperty("ch") + i;
            System.out.println(key + "  " + Arrays.toString(mapChromosome.get(key).getChromosome()) + "  " + mapChromosome.get(key).getValue()
                    + "   " + mapChromosome.get(key).getFitness() + "   " + mapChromosome.get(key).getPercentage() + "   " + mapChromosome.get(key).getSumPercentage());
        }
    }

    /*สร้าง invividual และ matingPool*/
    public String[] createMatingPool(Map<String, Chromosome> mapChromosome) {
        System.out.println(textXml.getProperty("ga.chromosome.invividualConvertMatingPool"));//"invividual=>matingPool");

        for (int i = 0; i < populationSize; i++) {
            int individual = actionGA.RandomTo(0, 100);            //สุ่มค่า invividual ตั้งแต่0-100
            System.out.print(individual + " ");
            for (int j = populationSize; j > 0; j--) {

                if (individual == 100) {                    //ถ้าinvividualแสดงว่าอยู่ช่วงchสุดท้ายแน่นอน
                    matingPool[i] = textXml.getProperty("ch") + j;
                    break;
                } else if (individual > mapChromosome.get(textXml.getProperty("ch") + j).getSumPercentage()) { //ถ้าinvividualอยู่ช่วงของchไหน
                    matingPool[i] = textXml.getProperty("ch") + (j + 1);        //ให้เก็บchนั้นไว้
                    break;
                } else {                                    //อื่นๆ ให้อยู่ ch แรก
                    matingPool[i] = textXml.getProperty("ch") + 1;
                }
            }

        }
        System.out.println("\n" + Arrays.toString(matingPool));   //แสดง matingPoolเช่น[ ch9,  ch12,  ch12,  ch10,  ch11]
        return matingPool;
    }

    /*การ crossover*/
    public Map<String, Chromosome> crossover(Map<String, Chromosome> mapChromosome, Map<String, Chromosome> mapNewChromosome) throws IOException {
        textXml.loadFromXML(uflp.getXML()); //โหลดไฟล์xmlที่ไว้เก็บstring
        int z = 0;
        int ch = 1;
        for (int i = 0; i < populationSize / 2; i++) {

            numRandom = Double.parseDouble(FormatPercentage.format(Math.random())); //สุ่มตัวเลขจำนวนจริงตั้งแต่0-1
            System.out.print(textXml.getProperty("random") + (numRandom)+" ");

            int[] parent1 = mapChromosome.get(matingPool[z++].trim()).getChromosome().clone();  //เอาchromosomeของแต่ลลคู่เก็บไว้ในparent1,2
            int[] parent2 = mapChromosome.get(matingPool[z++].trim()).getChromosome().clone();
            //System.out.println(Arrays.toString(parent1) + " " + Arrays.toString(parent2) + " " + z);
            if (popCrossover > numRandom) {                //ถ้าค่าที่สุ่มมา < popCrossover(0.75)      "เปลียนจาก< เป็น >"                                                               
                int chOld = ch;
                Integer[] lk = new Integer[chromosomeSize - 1];
                //int lk = m.RandomTo(0, chromosomeSize-1);         //สุ่มตำแหน่งจุดที่ทำการ crossover 
                int lkIndex = 0;
                int runLk = 0;
                for (int l = 0; l < lk.length; l++) {
                    lk[l] = l + 1;
                }
                Collections.shuffle(Arrays.asList(lk));
                //System.out.println("+++++++++++++++" + Arrays.toString(lk));
                while (ch < (chOld + 2)) {

                    System.out.print(textXml.getProperty("ga.crossover")+" ");//crossover
                    System.out.print(matingPool[ch - 1]+" ");
                    mapNewChromosome.put(textXml.getProperty("ch") + (ch++), new Chromosome(actionGA.combine(parent1.clone(), parent2.clone(), lk[lkIndex]).clone())); //สลับตำแหน่ง parent1 กับ parent2 ตามlk แล้วเก็บไว้ใน mapอันใหม่
                    System.out.println(matingPool[ch - 1] + " "+textXml.getProperty("index") + (lk[lkIndex]));

                    mapNewChromosome.put(textXml.getProperty("ch") + (ch++), new Chromosome(actionGA.combine(parent2.clone(), parent1.clone(), lk[lkIndex]).clone())); //สลับตำแหน่ง parent2 กับ parent1 ตามlk แล้วเก็บไว้ใน mapอันใหม่ 
                    //System.out.println("p" + Arrays.toString(parent1.clone()) + " " + Arrays.toString(parent2.clone()));
                    //System.out.println(">" + Arrays.toString(new Chromosome(m.combine(parent1.clone(), parent2.clone(), lk[lkIndex]).clone()).getChromosome()) + " \n>" + Arrays.toString(new Chromosome(m.combine(parent2.clone(), parent1.clone(), lk[lkIndex]).clone()).getChromosome()));
                    //if(!mapNewChromosome.get("ch"+(ch-2)).getChromosome().toString().contains("1"))
                    if (!Arrays.toString(mapNewChromosome.get(textXml.getProperty("ch") + (ch - 1)).getChromosome()).contains("1")) {
                        ch -= 2;
                        //System.out.println("เกิด [000]-1");
                        //System.err.println("เกิด [000]-1");
                        lkIndex++;

                        if (lkIndex == lk.length) {

                            int[] a = mapNewChromosome.get(textXml.getProperty("ch") + (ch + 1)).getChromosome();
                            int x = actionGA.RandomTo(1, chromosomeSize);
                            int lkNew = 0;
                            for (int Lk = 0; Lk < x; Lk++) {
                                lkNew = actionGA.RandomTo(0, chromosomeSize - 1);
                                a[lkNew] = 1;

                            }
                            //System.out.println("lkIndex == lk.length" + lkIndex + "=" + (lk.length) + "x: " + x + "lkNew" + lkNew);
                            mapNewChromosome.get(textXml.getProperty("ch") + (ch + 1)).setChromosome(a);
                            ch += 2;
                        }

                    } else if (!Arrays.toString(mapNewChromosome.get(textXml.getProperty("ch") + (ch - 2)).getChromosome()).contains("1")) {
                        ch -= 2;
                        //System.out.println("เกิด [000]-2");
                        //System.err.println("เกิด [000]-2");
                        lkIndex++;
                        

                        if (lkIndex == lk.length) {

                            int[] a = mapNewChromosome.get(textXml.getProperty("ch") + (ch)).getChromosome();
                            int x = actionGA.RandomTo(1, chromosomeSize);

                            int lkNew = 0;
                            for (int Lk = 0; Lk < x; Lk++) {
                                lkNew = actionGA.RandomTo(0, chromosomeSize - 1);
                                a[lkNew] = 1;

                            }
                            //System.out.println("lkIndex == lk.length" + lkIndex + "=" + (lk.length) + "x: " + x + "lkNew" + lkNew);
                            mapNewChromosome.get(textXml.getProperty("ch") + (ch)).setChromosome(a);
                            ch += 2;
                        }
                    }
                }

            } else {                                            //ถ้าค่าที่สุ่มมา>=popCrossover(0.75) ก็เก็บ chromosome เดิม ไว้ใน map อันใหม่
                System.out.println("");
                mapNewChromosome.put(textXml.getProperty("ch") + (ch++), new Chromosome(parent1));
                mapNewChromosome.put(textXml.getProperty("ch") + (ch++), new Chromosome(parent2));
            }
        }

        return mapNewChromosome;
    }

    /*ผ่าเหล่าลูก*/
    public void onePointMutation(Map<String, Chromosome> mapNewChromosome){
        for (int i = 1; i <= populationSize; i++) {
            int lk = actionGA.RandomTo(1, chromosomeSize - 1); //สุ่มตำแหน่ง
            //System.out.println(lk);
            numRandom = Double.parseDouble(FormatPercentage.format(Math.random())); //สุ่มตัวเลขจำนวนจริงตั้งแต่0-1
            System.out.print(textXml.getProperty("random")+ (numRandom)+" ");  //แสเงเลขที่สุ่มมา
            int z = 0;
            if (popMutation > numRandom) {          //ถ้า เลขที่สุ่มมา < popMutation(0.5) แสดงว่าต้องทำMutation        "เปลียนจาก< เป็น >"                 
                System.out.println(textXml.getProperty("ga.mutation")+" "+textXml.getProperty("ch")+ i + " "+textXml.getProperty("index") + (lk + 1)); //แสดงchที่จะMutation และตำแหน่งที่จะทำ
                int[] a = mapNewChromosome.get(textXml.getProperty("ch") + i).getChromosome();       // a เก็บ Chromosome ของ ch นั้นไว้
                //System.out.println(Arrays.toString(a));
                if (a[lk] == 1 && mapNewChromosome.get(textXml.getProperty("ch") + i).getValue() > 1) {                   //ถ้าค่าchromosomeตำแหน่งที่สุ่มมามีค่าเป็น1 จะสลับเป็น 0       
                    a[lk] = 0;
                } else if (a[lk] == 1 && mapNewChromosome.get(textXml.getProperty("ch") + i).getValue() <= 1) {//กรณีป้องกันไม่ให้เกิดเหตุการonePointแล้วได้ [000] เช่น [001] =>สลับตำแหน่ง3 => [000]
                    int lkOld = lk;
                    do {
                        lk = actionGA.RandomTo(1, chromosomeSize - 1);
                    } while (lk == lkOld);
                    System.out.println(textXml.getProperty("ga.mutation.new")+" "+textXml.getProperty("ch")+ i + " "+textXml.getProperty("random.new") + (lk + 1));
                    a[lk] = 1;
                } else {                            //ถ้าค่าchromosomeตำแหน่งที่สุ่มมามีค่าเป็น0 จะสลับเป็น 1
                    a[lk] = 1;
                }

                //System.out.println(Arrays.toString(a));
                mapNewChromosome.get(textXml.getProperty("ch") + i).setChromosome(a);    //setค่าChromosome ใหม่
            } else {
                System.out.println("");
            }

        }

    }

    /*หาผมลรวม Fitness ของลูก*/
    public void sumFitnessOffspring(boolean status, Map<String, Chromosome> mapNewChromosome, Map<String, Chromosome> mapOldChromosome, ActionUFLP uflp, Properties textXml, TestUFLP tUFLP) {
        //offspring
        setMinFitness(Double.MAX_VALUE);
        minChromosome = new int[chromosomeSize];
        indiv = new int[chromosomeSize];
        sumFitness = 0;
        for (int i = 1; i <= populationSize; i++) {

            int value = 0;
            String dec = Arrays.toString(mapNewChromosome.get(textXml.getProperty("ch") + i).getChromosome());       //เก็บChromosomeของchนั้นๆ
            dec = dec.substring(1, dec.length() - 1).replaceAll(", ", "");          //ทำให้อยู่ในรูปstring
            for (int j = 0; j < dec.length(); j++) {
                if (dec.charAt(j) == '1') { //ถ้ามีเลข1ก็นับไว้
                    value++;
                }
            }
            //int value = Integer.parseInt(dec, 2);                   //นำไปแปลงให้เป็นเลขฐาน10
            mapNewChromosome.get(textXml.getProperty("ch") + i).setValue(value);
            mapNewChromosome.get(textXml.getProperty("ch") + i).setFitness(tUFLP.mainTestold(mapNewChromosome.get(textXml.getProperty("ch") + i).getChromosome(), uflp, textXml)); //หาค่าFitness   c.setFitness( tUFLP.mainTestold(indiv, uflp, textXml)); 
            // mapNewChromosome.get("ch" + i).setFitness(searchC(mapNewChromosome.get("ch" + i).getChromosome(),dec,mapOldChromosome,uflp,textXml,tUFLP));
            sumFitness += mapNewChromosome.get(textXml.getProperty("ch") + i).getFitness();

            if (mapNewChromosome.get(textXml.getProperty("ch") + i).getFitness() < getMinFitness() && status == true) { //status==true คือหลังทำone point แล้ว

                setMinFitness(mapNewChromosome.get(textXml.getProperty("ch") + i).getFitness());
                setMinChromosome(mapNewChromosome.get(textXml.getProperty("ch") + i).getChromosome());
                setMinCh(textXml.getProperty("ch") + i);
            }

        }

        if (getMinFitness() < getBestFitness() && status == true) {
            //System.out.println("yerrrrrr");
            setBestFitness(getMinFitness());
            setBestChromosome(getMinChromosome());

        }
        //System.out.println("sumFitness:" + sumFitness);
    }

    public double searchC(int[] newChromosome, String decNew, Map<String, Chromosome> mapOldChromosome, ActionUFLP uflp, Properties textXml, TestUFLP tUFLP) {
        for (int i = 1; i <= populationSize; i++) {
            int value = 0;
            String dec = Arrays.toString(mapOldChromosome.get(textXml.getProperty("ch") + i).getChromosome());
            dec = dec.substring(1, dec.length() - 1).replaceAll(", ", "");
            if (decNew.equals(dec)) {
                return mapOldChromosome.get(textXml.getProperty("ch") + i).getFitness();
            }
        }
        return tUFLP.mainTestold(newChromosome, uflp, textXml);
    }

    //----------------get set------------------
    public double getPopMutation() {
        return popMutation;
    }

    public double getPopCrossover() {
        return popCrossover;
    }

    public double getArrCrossRandom() {
        return numRandom;
    }

    public void setArrCrossRandom(double arrCrossRandom) {
        this.numRandom = arrCrossRandom;
    }

    public String[] getMatingPool() {
        return matingPool;
    }

    public void setMatingPool(String[] matingPool) {
        this.matingPool = matingPool;
    }

    public DecimalFormat getFormatPercentage() {
        return FormatPercentage;
    }


    public double getSumPercentage() {
        return sumPercentage;
    }

    public void setSumPercentage(double sumPercentage) {
        this.sumPercentage = sumPercentage;
    }

    public int[] getIndiv() {
        return indiv;
    }

    public void setIndiv(int[] indiv) {
        this.indiv = indiv;
    }

    public int getChromosomeSize() {
        return chromosomeSize;
    }

    public void setChromosomeSize(int chromosomeSize) {
        this.chromosomeSize = chromosomeSize;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getSumFitness() {
        return sumFitness;
    }

    public void setSumFitness(double sumFitness) {
        this.sumFitness = sumFitness;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    public double getMinFitness() {
        return minFitness;
    }

    public void setMinFitness(double minFitness) {
        this.minFitness = minFitness;
    }

    public String getMinCh() {
        return minCh;
    }

    public void setMinCh(String minCh) {
        this.minCh = minCh;
    }

    public int[] getMinChromosome() {
        return minChromosome;
    }

    public void setMinChromosome(int[] minChromosome) {
        this.minChromosome = minChromosome;
    }

    public int[] getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(int[] bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

}
