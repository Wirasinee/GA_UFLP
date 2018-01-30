package pUflp;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
    String path;
    FileWriter writer;
    BufferedWriter buffer;
    
    public ActionFiles(){}

    public ActionFiles(String path) throws IOException {
        this.path = path;
        writer = new FileWriter(path);
        buffer = new BufferedWriter(writer);
    }

    public void write(String text) throws IOException {
        buffer.write(text);
    }

    public void nextLine() throws IOException {
        buffer.newLine();
    }
    
    public void close() throws IOException{
        buffer.close();
    }

    public void setPath(String path) throws IOException {
        this.path = path;
        writer = new FileWriter(path);
        buffer = new BufferedWriter(writer);
    }
    
    public String getNameFile(){
        Path p = Paths.get(path);
        String namefile = p.getFileName().toString();
        return namefile;
    }
    
   
    
    public ArrayList<String> readFile(String path){
		java.io.File file = new java.io.File(path);
		ArrayList<String> al = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
                        
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
                                String[] arr1 = line.trim().split("\\s+");
                                for(int i=0;i<arr1.length;i++){
                                    
                                     al.add(arr1[i]);
                                    
                                }
                         
                        }
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return al;
   
    }
    
    public ArrayList<Double> readFileDouble(String path){
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
    
    public ArrayList<Integer> readFileInt(String path){
		java.io.File file = new java.io.File(path);
		ArrayList<Integer> al = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
                        
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
                                String[] arr1 = line.trim().split("\\s+");
                                for(int i=0;i<arr1.length;i++){
                                    
                                     al.add(Integer.parseInt(arr1[i]));
                                    
                                }
                         
                        }
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return al;
   
    }
    
    public void listPathFile(String directoryName, ArrayList<String> files) {
        //String directoryName = "D:\\test\\Istanze";

        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(String.valueOf(file));
            } else if (file.isDirectory()) {
                listPathFile(file.getAbsolutePath(), files);
            }

        }
    }
    
    public void creatingDirectory(String directoryName) {
        //String directoryName = "D:\\test\\Istanze";

        Path newDirectoryPath = Paths.get(directoryName);

        if (!Files.exists(newDirectoryPath)) {
            try {
                Files.createDirectory(newDirectoryPath);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }
 
}

    

