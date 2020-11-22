

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
    static List<Integer> listColor;
    static int preInitalizedColors[];
    static int table[][];
    static int support[];
    static List<St_Obj1> list;
    static Set<Integer> set;
    private static Scanner scanner;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //File file = new File("randomGraphOutput");
        File file = new File("FILES/input");
        PrintStream fileStream = new PrintStream(new File("output.txt"));

        scanner = new Scanner(file);
        int vertices = scanner.nextInt();           //read input
        int edges = scanner.nextInt();
        System.out.println("Vertices: " + vertices + " Edges: " + edges);
        graph = new int[vertices][vertices];          //initialize graph
        color = new int[vertices];             //initialize color
        set = new HashSet();

        for (int i = 0; i < edges; i++) {
            // read input
            int xVertex = scanner.nextInt();
            int yVertex = scanner.nextInt();
            // undirected graph
            graph[xVertex - 1][yVertex - 1] = 1;
            graph[yVertex - 1][xVertex - 1] = 1;
        }
        int count = 0;
        listColor = new ArrayList<>();
        InitializeColors(vertices, count);

        int max_degree = -1;
        for (int i = 0; i < vertices; i++) {
            int temp = degree(graph, i);
            if (temp > max_degree)
                max_degree = temp;        //calculate max degree in graph
        }

        table = new int[vertices][max_degree + 2];

        for (int i = 0; i < preInitalizedColors.length; i++) {
            int index = preInitalizedColors[i];
            int col = color[index];
            for (int j = 0; j < graph[index].length; j++) {   //for every node go to it's adjacent node and if colored replace it's positon in table as 1
                if (graph[index][j] != 0) {
                    table[j][col] = 1;
                }
            }
        }

        support = new int[vertices];

        GetMaxDegree();      //calculate list

        while (listColor.size() != vertices) {
            St_Obj1 o = list.get(0);
            for (int j = 1; j < table[o.index].length; j++) {      //for every ele first zero is taken and index is taken as it's color
                if (table[o.index][j] == 0) {
                    color[o.index] = j;
                    for (int k = 0; k < graph[o.index].length; k++) {
                        if (graph[o.index][k] != 0) {
                            table[k][j] = 1;
                        }
                    }
                    listColor.add(o.index);
                    listColor.sort(null);
                    GetMaxDegree();
                    break;
                }
            }
        }

        int t = set.size();
        for (int i = 0; i < vertices; i++) {
            if (color[i] != 0)
                set.add(color[i]);
        }


        System.out.println("Maximum number of edges : " + (vertices * (vertices - 1) / 2));
        System.out.println("Total colors : " + set.size());
        System.out.println("Addl. new colors used : " + (set.size() - t));
        fileStream.println(String.valueOf(set.size() - t));   //calculating no of new colors used

        for (int i = 0; i < vertices; i++) {
            System.out.print(color[i] + " ");
            fileStream.print(String.valueOf(color[i]) + " ");     //printing output
        }

    }

    private static void InitializeColors(int vertices, int count) {
        for (int i = 0; i < vertices; i++) {
            // reading colors from input
            color[i] = scanner.nextInt();
            if (color[i] != 0) {
                set.add(color[i]);
                count++;
            }
        }
        preInitalizedColors = new int[count];
        count = 0;
        for (int i = 0; i < color.length; i++) {
            if (color[i] != 0) {
                preInitalizedColors[count] = i;
                listColor.add(i);
                count++;
            }
        }
    }

    public static void GetMaxDegree() {  //finds the list in which elements are ordered descending by coloredAdjNodes value of node i
        list = new ArrayList<St_Obj1>();
        list.clear();
        for (int i = 0; i < graph.length; i++) {
            if (Collections.binarySearch(listColor, i) < 0) {
                support[i] = (coloredAdjNodes(graph, i, preInitalizedColors, color));
                St_Obj1 s = new St_Obj1();
                s.index = i;
                s.value = support[i];
                list.add(s);
            }
        }


        MySt_Cmp1 cmp = new MySt_Cmp1();
        list.sort(cmp);
    }

    public static int uncoloredAdjCount(int graph[][], int i, int color[]) { //finds no of nodes not colored and adjacent to node i
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

    public static int coloredAdjNodes(int graph[][], int i, int color[], int c[]) {    //finds no of nodes colored and adjacent to node i
        int count = 0;
        List<Integer> l = new ArrayList<>();
        for (int j = 0; j < graph[i].length; j++) {
            if (graph[i][j] != 0) {
                if (Collections.binarySearch(listColor, j) >= 0 && !l.contains(c[j])) {
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
