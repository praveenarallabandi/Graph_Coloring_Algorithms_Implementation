

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SuperGraphColoring {
    static int graph[][];
    static int color[];
    static List<Integer> list_color;
    static int colored_given[];
    static int table[][];
    static int support[];
    static List<St_Obj1> list;
    static Set<Integer> set;
    private static Scanner sc;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //File file = new File("randomGraphOutput");
        File file = new File("input");
        PrintStream fileStream = new PrintStream(new File("output.txt"));

        sc = new Scanner(file);
        int v = sc.nextInt();           //read input
        int e = sc.nextInt();
        System.out.println("Vertices: " + v + " Edges: " + e);
        graph = new int[v][v];          //initialize graph
        color = new int[v];             //initialize color
        set = new HashSet();

        for (int i = 0; i < e; i++) {
            int x = sc.nextInt();       //reading edges and adding then to graph
            int y = sc.nextInt();
            graph[x - 1][y - 1] = 1;        //undirected graph so this step is performed
            graph[y - 1][x - 1] = 1;
        }
        int count = 0;
        list_color = new ArrayList<>();
        for (int i = 0; i < v; i++) {

            color[i] = sc.nextInt();   //reading colors
            if (color[i] != 0)
                set.add(color[i]);
            if (color[i] != 0)
                count++;
        }
        colored_given = new int[count];    //contains nodes which are already colored
        count = 0;
        for (int i = 0; i < color.length; i++) {
            if (color[i] != 0) {
                colored_given[count] = i;
                list_color.add(i);         //contains nodes which are already colored
                count++;
            }
        }

        int max_degree = -1;
        for (int i = 0; i < v; i++) {
            int temp = degree(graph, i);
            if (temp > max_degree)
                max_degree = temp;        //calculate max degree in graph
        }

        table = new int[v][max_degree + 2];

        for (int i = 0; i < colored_given.length; i++) {
            int index = colored_given[i];
            int col = color[index];
            for (int j = 0; j < graph[index].length; j++) {   //for every node go to it's adjacent node and if colored replace it's positon in table as 1
                if (graph[index][j] != 0) {
                    table[j][col] = 1;
                }
            }
        }

        support = new int[v];

        GetMaxDegree();      //calculate list

        while (list_color.size() != v) {
            St_Obj1 o = list.get(0);
            for (int j = 1; j < table[o.index].length; j++) {      //for every ele first zero is taken and index is taken as it's color
                if (table[o.index][j] == 0) {
                    color[o.index] = j;
                    for (int k = 0; k < graph[o.index].length; k++) {
                        if (graph[o.index][k] != 0) {
                            table[k][j] = 1;
                        }
                    }
                    list_color.add(o.index);
                    list_color.sort(null);
                    GetMaxDegree();
                    break;
                }
            }
        }

        int t = set.size();
        for (int i = 0; i < v; i++) {
            if (color[i] != 0)
                set.add(color[i]);
        }


        System.out.println("Maximum number of edges : " + (v * (v - 1) / 2));
        System.out.println("Total colors : " + set.size());
        System.out.println("Addl. new colors used : " + (set.size() - t));
        fileStream.println(String.valueOf(set.size() - t));   //calculating no of new colors used

        for (int i = 0; i < v; i++) {
            System.out.print(color[i] + " ");
            fileStream.print(String.valueOf(color[i]) + " ");     //printing output
        }

    }

    public static void GetMaxDegree() {  //finds the list in which elements are ordered descending by no_colored value of node i
        list = new ArrayList<St_Obj1>();
        list.clear();
        for (int i = 0; i < graph.length; i++) {
            if (Collections.binarySearch(list_color, i) < 0) {
                support[i] = (no_colored(graph, i, colored_given, color));
                St_Obj1 s = new St_Obj1();
                s.index = i;
                s.value = support[i];
                list.add(s);
            }
        }


        MySt_Cmp1 cmp = new MySt_Cmp1();
        list.sort(cmp);
    }

    public static int no_not_colored(int graph[][], int i, int color[]) { //finds no of nodes not colored and adjacent to node i
        int count = 0;
        for (int j = 0; j < graph[i].length; j++) {
            if (graph[i][j] != 0) {
                if (Arrays.binarySearch(color, j) < 0) {

                    count++;
                }
            }
        }
        return count;
    }

    public static int no_colored(int graph[][], int i, int color[], int c[]) {    //finds no of nodes colored and adjacent to node i
        int count = 0;
        List<Integer> l = new ArrayList<>();
        for (int j = 0; j < graph[i].length; j++) {
            if (graph[i][j] != 0) {
                if (Collections.binarySearch(list_color, j) >= 0 && !l.contains(c[j])) {
                    count++;
                    l.add(c[j]);
                }
            }
        }
        return count;
    }

    public static int degree(int graph[][], int i) {            // method to find degree of node i
        int count = 0;
        for (int j = 0; j < graph[i].length; j++) {
            if (graph[i][j] != 0) {
                count++;
            }
        }
        return count;
    }
}

class St_Obj1 {
    int index;
    int value;
}

class MySt_Cmp1 implements Comparator<St_Obj1> {

    @Override
    public int compare(St_Obj1 o1, St_Obj1 o2) {
        return o2.value - o1.value;
    }

}
