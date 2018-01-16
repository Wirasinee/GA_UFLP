/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import pUflp.ActionFiles;
import pUflp.ActionUFLP;
import pUflp.Locations;
import pUflp.TestUFLP;
import java.util.Random;

/**
 *
 * @author JuthamasS
 */
import java.text.DecimalFormat;

public class MainAnt {

    static Scanner in = new Scanner(System.in);
    static ActionFiles file = new ActionFiles();
    static Locations location = new Locations();
    static ArrayList<Double> inputFile = null;
    static Properties textXml = new Properties();
    static ActionUFLP uflp = new ActionUFLP();
    static TestUFLP tUFLP = new TestUFLP();
    static Random random = new Random();
    static DecimalFormat df = new DecimalFormat("0.00000");

    public static void main(String[] args) throws IOException {
        location.getLocations(); ///สร้างตำแหน่งของสถานีกับลูกค้า
        MainAnt mc = new MainAnt();
        textXml.loadFromXML(uflp.getXML()); //โหลดไฟล์xmlที่ไว้เก็บstring
        try {
            mc.inputFile = file.readFile("D:\\DistanceFile.txt");//D:\\test\\2\\3\\DistanceFile.txt
        } catch (Exception e) {
            System.err.println(textXml.getProperty("email.support"));
        }
        uflp.inputAll(mc.inputFile, textXml); //แสดงข้อมูล ระยะทางสถานีไปโกดัง ต้นทุนสถานี
//-------------------------------------------------------------------------------
        double T[] = new double[uflp.getN()];
        double eta[] = new double[uflp.getN()];
        double percentage[] = new double[uflp.getN()];
//char location[] = new char[10];
        double summation = 0;
        int alpha = 1;
        int beta = 2;
        int arrANS[] = new int[uflp.getN()];
        double p[] = new double[uflp.getN()];

        System.out.println();
        System.out.println(textXml.getProperty("ant"));

        for (int i = 0; i < uflp.getN(); i++) {
            T[i] = 0.1;
            System.out.println("T" + (i + 1) + " : " + T[i]);
            eta[i] = 1 / uflp.getW()[i]; //(random.nextInt(2000)+1000.0); //(1000 + (int)(Math.random() * (5000))
            String output = df.format(eta[i]);
            System.out.println(textXml.getProperty("w.(w)")+": " + uflp.getW()[i]);//ต้นทุนแต่ละสถานี(w)
            System.out.println("ETA" + (i + 1) + " : " + eta[i]);
            p[i] = (Math.pow(T[i], alpha) * Math.pow(eta[i], beta));
            summation += (Math.pow(T[i], alpha) * Math.pow(eta[i], beta));
            System.out.println("p" + (i + 1) + " : " + p[i]);
//String x = df.format(summation);
            System.out.println("sum : " + summation);
            System.out.println("-------------------------------------");
        }
        System.out.println("sumT&ETA : " + summation);
        System.out.println("------------------------------");
//-----------------------------------------------------------------------------
        System.out.println("ค่า P หลังจากหารด้วย summation");
        for (int i = 0; i < uflp.getN(); i++) {
            p[i] = p[i] / summation;
            System.out.println(p[i]);
        }
        System.out.println("-------------------------------");
//---------------------------------------------------------------------------
        double temp = 0;
        for (int i = 0; i < uflp.getN(); i++) {

            if (i == 0) {
                temp += p[0];
                percentage[0] = p[0];
                System.out.print("P" + (i + 1) + " : " + p[0] + " ");
                System.out.println("ร้อยละ" + (i + 1) + " : " + percentage[0]);
            } else {
                temp += p[i];
                percentage[i] = temp;
                System.out.print("P" + (i + 1) + " : " + p[i] + " ");
                System.out.println("ร้อยละP" + (i + 1) + " : " + percentage[i]);
            }
        }
//------------------------------------------------------------------------------------
        double r = Math.random();
        System.out.println("ค่าสุ่มเลือกเมือง : " + r);

        for (int i = 0; i <= uflp.getN(); i++) {
            if (r <= percentage[i]) {
                arrANS[0] = i;
                break;
            }
        }
        System.out.println("เมืองที่เลือกไป : P" + (arrANS[0] + 1));

    }
}
