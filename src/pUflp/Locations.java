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
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Locations {
    public void getLocations() throws IOException{
        Random random = new Random();
        
        Scanner in = new Scanner(System.in);
        System.out.print("จำนวนสถานี (n) :");
        int n = in.nextInt();;// in.nextInt();
        System.out.print("จำนวนลูกค้า (m) :");
     int m = in.nextInt();
        int warehouse_locations[][] = new int[n][2];
        int customer_locations[][] = new int[m][2];
        double distance[][] = new double[n][m];
        double cost[] = new double[n];
        
        //String separator = System.getProperty("file.separator");
        String filepath_random_location = "D:\\"+"LocationFile.txt"; 
        //C:\Users\Admins\Desktop
        
        File file_random_location = new File(filepath_random_location);
        FileWriter writer_random_location = new FileWriter(file_random_location, false);  //True = Append to file, false = Overwrite
        
        System.out.println("Warehouse Locations\n");
        writer_random_location.write("warehouse locations: "+n+"\r\n");
        for(int i=0;i<n;i++){         //warehouse
            warehouse_locations[i][0] = random.nextInt(1500)+500;
            warehouse_locations[i][1] = random.nextInt(1500)+500;
            writer_random_location.write("( "+warehouse_locations[i][0]+" , "+warehouse_locations[i][1]+" )\r\n");
            System.out.println("( "+warehouse_locations[i][0]+" , "+warehouse_locations[i][1]+" )\n");
        }
        System.out.println("Customer Locations\n");
        writer_random_location.write("customer locations: "+m+"\r\n");
        for(int j=0;j<m;j++){         //customer
            customer_locations[j][0] = random.nextInt(1500)+500;
            customer_locations[j][1] = random.nextInt(1500)+500;
            writer_random_location.write("( "+customer_locations[j][0]+" , "+customer_locations[j][1]+" )\r\n");
            System.out.println("( "+customer_locations[j][0]+" , "+customer_locations[j][1]+" )\n");
        }
        writer_random_location.close();
        
        System.out.println("All distance from each warehouse to each customer\n");
        String filepath_distance_location = "D:\\"+"DistanceFile.txt";
        File file_distance_location = new File(filepath_distance_location);
        FileWriter writer_distance_location = new FileWriter(file_distance_location, false);
        
        writer_distance_location.write(n+" "+m+"\r\n");
        System.out.println("Cost of each warehouse\n");
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                double a = abs(warehouse_locations[i][0])*abs(warehouse_locations[i][0]);   //System.out.println(a);
                double b = abs(warehouse_locations[i][1])*abs(warehouse_locations[i][1]);   //System.out.println(b);
                cost[i] = sqrt(a+b);
                
            }writer_distance_location.write((new DecimalFormat("0.0000").format(cost[i]))+" \r\n");
            System.out.println((new DecimalFormat("0.0000").format(cost[i]))+" \n");
        }
        System.out.println("Cost of each customer from warehouse\n");
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                double a = abs(warehouse_locations[i][0]-customer_locations[j][0])*abs(warehouse_locations[i][0]-customer_locations[j][0]);   //System.out.println(a);
                double b = abs(warehouse_locations[i][1]-customer_locations[j][1])*abs(warehouse_locations[i][1]-customer_locations[j][1]);   //System.out.println(b);
                distance[i][j] = sqrt(a+b);         //System.out.println(sqrt(25));   à¹€à¸­à¸­à¹€à¸£à¹ˆà¸­à¸§à¹ˆà¸²à¸„à¹ˆà¸²à¸—à¸µà¹ˆà¹„à¸”à¹‰à¹„à¸¡à¹ˆà¹€à¸›à¹‡à¸™à¸•à¸±à¸§à¹€à¸¥à¸‚  à¹€à¹€à¸•à¹ˆà¸—à¸³à¸ªà¹€à¹€à¸„à¸§à¸£à¸¹à¸— 25 à¹„à¸”à¹‰à¸„à¹ˆà¸²à¸›à¸�à¸•à¸´à¸™à¸°
                writer_distance_location.write((new DecimalFormat("0.0000").format(distance[i][j]))+" ");
//                DecimalFormat dec = new DecimalFormat("0.00");
//                double a = dec.format(distance[i][j]);
                System.out.println((new DecimalFormat("0.0000").format(distance[i][j]))+" ");
            }
            writer_distance_location.write("\r\n");
            System.out.println();
        }
      
        writer_distance_location.close();
        
        System.out.println("Congraduate!!! Now your file written success!");
    }
}
