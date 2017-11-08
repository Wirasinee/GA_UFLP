package pUflp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wirasinee
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class testUFLP {

    static Scanner in = new Scanner(System.in);
    static ActionFiles file = new ActionFiles();
    
    //static double[] w;//ต้นทุนแต่ละโกดัง
    static double sumW = 0;//ต้นทุนรวม

    public static void main(String[] args) throws IOException {
        Locations location = new Locations();
        location.getLocations();
        ActionMainUFLP uflp = new ActionMainUFLP();
        Properties textXml = new Properties();
        textXml.loadFromXML(uflp.getXML());
        
        try {
            uflp.setInputFile(file.readFile("D:\\DistanceFile.txt"));//D:\\test\\2\\3\\DistanceFile.txt
        } catch (Exception e) {
            System.err.println(textXml.getProperty("email.support"));
        }
        uflp.inputAll(uflp.getInputFile(),textXml);
        
        
        double[][] distance = uflp.getDistance();
        double[] w=uflp.getW();
        double[] min = new double[uflp.getM()]; // [0,0,0]
        int[][] contectPeopleStatus = new int[uflp.getS()][uflp.getN()]; //จำนวนรวมของลูกค้าแต่ละโกดัง
        int[][] statusNearCustomer = new int[uflp.getM()][uflp.getN()]; // เก็บ โกดังที่ใกล้กับลูกค้า
        double C = Integer.MAX_VALUE;
        int[] openStatus = new int[uflp.getN()];
        int[] open = new int[uflp.getN()];
        double[] sumMinDistance = new double[uflp.getS()]; //ผลรวมเส้นทางสั่นสุดของแต่ละลูกค้า
        for (int q = 1; q <= uflp.getS(); q++) {
            String openString = String.format(("%" + uflp.getN() + "s"), Integer.toBinaryString(q)).replace(' ', '0');
            for (int i = 0; i < openString.length(); i++) {
                open[i] = (Integer.parseInt(openString.charAt(i) + ""));
            }

            //System.out.println(">" + Arrays.toString(open) + "  ||  ");
            statusNearCustomer = new int[uflp.getM()][uflp.getN()];
           for (int j = 0; j < uflp.getM(); j++) { 
            
                double minDistance = Integer.MAX_VALUE;
             for (int i = 0; i < uflp.getN(); i++) {  

                    if (open[i] == 1) { //ถ้าลองให้มันเปิด

                        if (minDistance >= distance[j][i]) { //ถ้าระยะทางลูกค้าไปโกดังเป็นเส้นทางสั่นสุด
                            
                            minDistance = distance[j][i];
                            
                            statusNearCustomer[j][i] = 1;              //ให้เปิดโกดัง
                            for (int z = i - 1; z >= 0; z--) {
                                statusNearCustomer[j][z] = 0;
                            }
                        }
                    }
                 
                }
                for (int c = 0; c < uflp.getN(); c++) {
                    if (statusNearCustomer[j][c] >= 1) {

                        contectPeopleStatus[q - 1][c] += statusNearCustomer[j][c];

                    }
                }
               // System.out.println(Arrays.toString(contectPeopleStatus[q]));
                min[j] = minDistance; //จะได้ระยะทางเส้นทางสั่นสุดของแต่ละลูกค้า
                
                sumMinDistance[q - 1] += min[j];
//System.out.println(Arrays.toString(sumMinDistance));
            }
            sumW = 0;
            for (int i = 0; i < uflp.getN(); i++) {
                if (contectPeopleStatus[q - 1][i] >= 1) {
                    sumW += w[i];

                }
            }
            //System.out.println("--" + sumW + " " + sumMinDistance[q - 1]);
            if ((sumW + sumMinDistance[q - 1]) < C) {

                C = sumW + sumMinDistance[q - 1];

                openStatus = open.clone();

            }
        }
        System.out.println("_________________________________");
        System.out.println(textXml.getProperty("status.service"));
        
        for (int q = 1; q <= uflp.getS(); q++) {
//            System.out.print("[" + String.format("%3s", Integer.toBinaryString(q)).replace(' ', '0') + "] ");
            for (int i = 0; i < uflp.getN(); i++) {
                System.out.print(contectPeopleStatus[q - 1][i] + " ");
            }
            System.out.println();

        }
        System.out.println("_________________________________");
        System.out.println(textXml.getProperty("C(S)") + C);
        System.out.println(textXml.getProperty("status.open") + Arrays.toString(openStatus));
        System.out.print(textXml.getProperty("status"));
        for (int j = 0; j < uflp.getN(); j++) {
            if (openStatus[j] >= 1) {
                System.out.print(j + 1 + " ");
            }
        }

    }

    
}
