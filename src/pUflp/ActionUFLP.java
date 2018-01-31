/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pUflp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wirasinee
 */

public class ActionUFLP {

    private int index = 0;
    private int m, n, s;
    private double[][] distance;
    private double[] w, demand;
    private ArrayList<Double> inputFile = null;

    public void inputAll(ArrayList<String> file, Properties textXml) throws IOException {
        
        setM(Integer.parseInt(file.get(index++))); //จ โกดัง
        System.out.println(textXml.getProperty("status.(m)") + ": " + m);
        setN(Integer.parseInt(file.get(index++))); //จ ลูกค้า
        System.out.println(textXml.getProperty("cutomer.(n)") + ": " + n);
        distance = new double[n][m];

        System.out.println(textXml.getProperty("capacity.and.w"));//[ความจุแต่ละสถานี(w)\\ต้นทุนแต่ละสถานี(w)]  
        //setCapa(new double[m]);
        setW(new double[m]);
        for (int i = 0; i < m; i++) {
            //capa[i] = file.get(index++); ไม่ต้องเก็บเพราะไม่ได้นำมาคำนวน
            index++;
            w[i] = Double.parseDouble(file.get(index++));
            System.out.println(textXml.getProperty("status") + (i + 1) + " : "+ w[i]);
        }


        demand = new double[n];
        for (int i = 0; i < n; i++) {
            demand[i] = Double.parseDouble(file.get(index++));
    

            for (int j = 0; j < m; j++) {

                distance[i][j] = Double.parseDouble(file.get(index++));
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(textXml.getProperty("demand.cutomer") + (i + 1) + " : " + demand[i]);
            System.out.print(textXml.getProperty("distance") + (i + 1) + ": ");
            for (int j = 0; j < m; j++) {

                System.out.print(distance[i][j] + " ");

            }
            System.out.println();
        }
        


    }

    public FileInputStream getXML() {

        FileInputStream is = null;
        try {
            is = new FileInputStream("src\\pUflp\\stringUFLP.xml");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestUFLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //load the xml file into properties format

            return is;
        } catch (Exception ex) {
            Logger.getLogger(TestUFLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double[] getDemand() {
        return demand;
    }

    public void setDemand(double[] demand) {
        this.demand = demand;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

  

    public int getS(int x) {
        return s = (x*getM());
    }

    public void setS(int s) {
        this.s = s;
    }

    public double[][] getDistance() {
        return distance;
    }

    public void setDistance(double[][] distance) {
        this.distance = distance;
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public ArrayList<Double> getInputFile() {
        return inputFile;
    }

    public void setInputFile(ArrayList<Double> inputFile) {
        this.inputFile = inputFile;
    }

   

}
/*
public class ActionUFLP {
    
    private int index = 0;
    private int n, m, s;
    private double[][] distance;
    private double[] w;
    private ArrayList<Double> inputFile = null;
    

    public void inputAll(ArrayList<Double> file, Properties textXml) {
        setN(file.get(index++).intValue()); //จ โกดัง
        System.out.println(textXml.getProperty("status.(n)") + ": " + n);
       
        setM(file.get(index++).intValue()); //จ ลูกค้า
        System.out.println(textXml.getProperty("cutomer.(m)") + ": " + m);

        distance = new double[m][n];
        System.out.println(textXml.getProperty("w"));
        setW(new double[n]);
        for (int i = 0; i < n; i++) {
            w[i] = file.get(index++);
            System.out.println(textXml.getProperty("status") + (i + 1) + " : " + w[i]);
        }
        System.out.println("distance[ลูกค้า\\สถานี]: ");//ระยะทาง
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                distance[j][i] = file.get(index++);
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.print("สถานี" + (i + 1) + ": ");
            for (int j = 0; j < m; j++) {

                System.out.print(distance[j][i] + " ");

            }
            System.out.println();
        } 
        

    }

    public FileInputStream getXML() {
        
        FileInputStream is = null;
        try {
            is = new FileInputStream("src\\pUflp\\stringUFLP.xml");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestUFLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //load the xml file into properties format

            return is;
        } catch (Exception ex) {
            Logger.getLogger(TestUFLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getS() {
        return s = ((((n - 1) * (n - 2)) / 2) + 1) + (((n * (n - 1)) / 2) + 1) + 1;
    }

    public void setS(int s) {
        this.s = s;
    }

    public double[][] getDistance() {
        return distance;
    }

    public void setDistance(double[][] distance) {
        this.distance = distance;
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public ArrayList<Double> getInputFile() {
        return inputFile;
    }

    public void setInputFile(ArrayList<Double> inputFile) {
        this.inputFile = inputFile;
    }

    public double searchC(int value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    

}*/
