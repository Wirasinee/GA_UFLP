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

public class TestUFLP {

    static Scanner in = new Scanner(System.in);
    static ActionFiles file = new ActionFiles();

    //static double[] w;//ต้นทุนแต่ละโกดัง
    static double sumW = 0;//ต้นทุนรวม


    public double mainTestold(int[] open, ActionUFLP uflp, Properties textXml) {
        
        int[] openStatus = new int[uflp.getN()];      
        double C = Integer.MAX_VALUE;
        double[] w = uflp.getW();
        double sumMinDistance = 0;
        double[] min = new double[uflp.getM()];
        int[] contectPeopleStatus = new int[uflp.getN()];
        double[][] distance = uflp.getDistance();
        
        int[][] statusNearCustomer = new int[uflp.getM()][uflp.getN()];

        for (int j = 0; j < uflp.getM(); j++) {
            double minDistance = Integer.MAX_VALUE; //ตัวแปรเก็บเส้นทางที่สันที่สุด (ระยะทางโกดังที่ใกล้ที่สุดของลูกค้าค้นนั้น)
            for (int i = 0; i < uflp.getN(); i++) {

                if (open[i] == 1) { //ถ้าลองให้มันเปิด เช่น [0,1,1]

                    if (minDistance >= distance[j][i]) { //ถ้าระยะทางลูกค้าไปโกดังเป็นเส้นทางสั่นกว่า

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

                    contectPeopleStatus[c] += statusNearCustomer[j][c]; //ดูว่ามีลูกค้ากี่ค้นเลือกโกดังที่เปิดแต่ละกรณี

                }
            }
            // System.out.println(Arrays.toString(contectPeopleStatus[q]));
            min[j] = minDistance; //จะได้ระยะทางเส้นทางสั่นสุดของแต่ละลูกค้า

            sumMinDistance += min[j];
//System.out.println(Arrays.toString(sumMinDistance));
        }
        sumW = 0;//ผลรวมต้นทุน
        for (int i = 0; i < uflp.getN(); i++) {
            if (open[i] == 1) {
                sumW += w[i];               //หาต้นทุนการเปิดโกดัง

            }
        }
       // System.out.println("--" + sumW + " " + sumMinDistance+" "+(sumW+sumMinDistance));
        if ((sumW + sumMinDistance) < C) {

            C = sumW + sumMinDistance;

            openStatus = open.clone();

        }
        return C;
        

    }



   
    

}
