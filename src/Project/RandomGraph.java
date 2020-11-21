
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomGraph {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        PrintStream fileStream = new PrintStream(new File("randomGraphOutput"));
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        fileStream.println(String.valueOf(n));
        fileStream.println(String.valueOf(m));
        Random rand = new Random();
        List<String> l = new ArrayList<>();
        int g[][] = new int[n][n];
        
        if(m>(n*(n-1))/2){
            System.out.println("#Edges out of bound for a given #Nodes");
            return;
        }
        for(int i=0;i<m;i++){
            int x = rand.nextInt(n)+1;
            int y = rand.nextInt(n)+1;
            
            while(l.contains(x+" "+y)){
                x = rand.nextInt(n)+1;
                y = rand.nextInt(n)+1;
            }
            l.add(x+" "+y);
            l.add(y+" "+x);
            g[x-1][y-1] =1;
            g[y-1][x-1] =1;
            // WriteToFile(x, y);
            System.out.println(x + " " + y);
            fileStream.println(String.valueOf(x + " " + y));
        }
        
        for(int i=0;i<n;i++){
            System.out.print("0 ");
            fileStream.print(String.valueOf("0 "));
        }
        
        int max_degree = -1;
        for(int i=0;i<n;i++){
            int count = 0;
            for(int j=0;j<n;j++){
                if(g[i][j]!=0){
                    count++;
                }
            }
            if(count>max_degree)
                max_degree = count;
        }
        
        System.out.println("\nMax Degree: "+max_degree);
        fileStream.close();
    }

    private static void WriteToFile(int x, int y) throws FileNotFoundException {
        PrintStream fileStream = null;
        try {
            fileStream = new PrintStream(new FileOutputStream("randomGraphOutput.txt", true));
            System.out.println(x + " " + y);
            fileStream.println(String.valueOf(x + " " + y));
        } catch (FileNotFoundException ex) {

        }
        catch (Exception e) {
            System.out.println(e);
        } finally {
            fileStream.close();
        }
    }
}

class Obj{
    int x,y;
}
