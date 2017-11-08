package pUflp;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wirasinee
 */
public class ActionFiles {
    public ArrayList<Double> readFile(String path){
		java.io.File file = new java.io.File(path);
		ArrayList<Double> al = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
                        
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
                                String[] arr1 = line.trim().split("\\s+");
                                for(int i=0;i<arr1.length;i++){
                                    
                                     al.add(Double.parseDouble(arr1[i]));
                                    
                                }
                         
                        }
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return al;
   
    }
}

    

