package graphColoringAlgorithms;
import modules.Colors;

import java.util.ArrayList;
import java.util.Objects;


public class GraphColoringBackTrackingForSudoku {

	boolean shuffle = false;

	public void colorGraph(Graph G) {
		//only 9 colors are used
		int colorsAvailable = 9;
		
		if(isGraphColorable(G, colorsAvailable)){
			System.out.println("With the available colors the graph was colorable");
		}
		else{
			System.out.println("With the available colors the graph was not colorable");
		}
		
	}

	public void printDescription() {
		// TODO Auto-generated method stub
		System.out.println("This is sudoku solver using backtracking graph coloring");
	}
	
	public boolean isSafe(ArrayList<Edge> edges){
		
		for (Edge e: edges){
			if (e.getStartVertex().color == e.getEndVertex().color)
				return false;
		}
		return true;
	}
	
	public boolean colorGraphUsingBackTrack(Graph G, ArrayList<Vertex> vertices, int index, int numberOfColors){
		
		//if all vertices are covered
		if (index == vertices.size())
			return true;
		//already colored with fixed color
		if (Objects.equals(vertices.get(index).props.get("fixed"),"1"))
			return colorGraphUsingBackTrack(G, vertices, index+1, numberOfColors);
		
		for (int i = 1; i <= numberOfColors; i++){
			
			//color vertex
			vertices.get(index).color = i;
			
			//check if it safe
			if (this.isSafe(G.getEdgesFromAdj(vertices.get(index)))){
				//color next vertex
				if (colorGraphUsingBackTrack(G, vertices, index+1, numberOfColors))
					return true;
				
			}
			//if next vertex was not colorable. reset the color and try next one
			vertices.get(index).color = -1; //reset color
		}
		
		return false;
	}
	
	public void toggleShuffle(){
		shuffle = !shuffle;
	}

	public boolean isGraphColorable(Graph G, int noOfColors) {
		// TODO Auto-generated method stub
		ArrayList<Vertex> vertices;
		
		if (this.shuffle)
			vertices = this.shuffleVertices(G.getVertices());
		else 
			vertices = G.getVertices();
		
		return this.colorGraphUsingBackTrack(G, vertices, 0, noOfColors);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "Files/Sudoku";
		Graph G = FileIO.readSudokuAndCreateGraph(path, new GraphAdjListImpl());
		G.setGraphColoringTechnique(new GraphColoringBackTrackingForSudoku());
		G.colorGraph();
		Colors.printSudoku(G);
	}
}

public class Edge {

	public HashMap<String, String> props;
	private Vertex start;
	private Vertex end;

	public Edge(){
		this.props = new HashMap<String,String>();
	}

	public Edge(Vertex s, Vertex e){
		this.start = s;
		this.end = e;
	}

	public void addProp(String property, String value){
		this.props.put(property, value);
	}

	public void setStartVertex(Vertex v){
		this.start = v;
	}

	public void setEndVertex(Vertex v){
		this.end = v;
	}

	public Vertex getStartVertex(){
		return this.start;
	}

	public Vertex getEndVertex(){
		return this.end;
	}
}

package graph;
		import java.util.HashMap;

public class Vertex {

	public HashMap<String, String> props;
	public int color;

	public Vertex(){
		this.props = new HashMap<String, String>();
		this.color = -1;
	}

	public void addProp(String property, String value){
		this.props.put(property, value);
	}

}

