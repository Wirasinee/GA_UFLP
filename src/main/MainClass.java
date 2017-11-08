/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
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
    static MainClass_GA mainClassGa = new MainClass_GA();
    static ActionMainUFLP uflp = new ActionMainUFLP();
    public static void main(String[] args) throws IOException{
        location.getLocations();
        MainClass mc = new MainClass();
        textXml.loadFromXML(uflp.getXML()); //โหลดไฟล์xmlที่ไว้เก็บstring
        try {
            mc.inputFile = file.readFile("D:\\DistanceFile.txt");//D:\\test\\2\\3\\DistanceFile.txt
        } catch (Exception e) {
            System.err.println(textXml.getProperty("email.support"));
        }
        uflp.inputAll(mc.inputFile,textXml);
    
    }
    
}
