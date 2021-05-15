import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

  // Read in files
  // Each instance is an array of int - 1/0
  // final column (array.length - 1 = label.

  public Util(){}

  public ArrayList<int[]> readFile(String filename){

    ArrayList<int[]> instances = new ArrayList<>();

    File f  = new File(filename);
    try {
      BufferedReader br = new BufferedReader(new FileReader(f));
      String line;
      while((line = br.readLine()) != null){
        int[] attributes = new int[line.length()/6];
        Scanner sc = new Scanner(line);
        int i = 0;
        while(sc.hasNextInt()){
          attributes[i] = sc.nextInt();
          i++;
        }
        instances.add(attributes);
      }

    }catch (Exception e){
      e.printStackTrace();
    }

    return instances;

  }



}
