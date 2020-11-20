package modules;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Metadata {

    public int vertices;
    public int nodes;
    public int[] colors = new int[18];

    public Metadata(int vertices, int nodes, int[] colors) {

        this.vertices = vertices;
        this.nodes = nodes;
        this.colors = colors;
    }
}