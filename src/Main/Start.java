package Main;
import graph.Graph;
import graph.GraphAdjListImpl;
import graph.GraphAdjMatrixImpl;
import graphColoringAlgorithms.GraphColoringBackTracking;
import graphColoringAlgorithms.GraphColoringGreedy;
import graphColoringAlgorithms.GraphColoringWelshPowell;
import modules.Colors;
import modules.FileIO;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "Files/sample2";
		//streamFileAndUpdateGraph(path);

		//find which graph should be implemented
		/*int numberOfVertices = FileIO.GraphToCreate(path);
		System.out.println("numberOfVertices - " + numberOfVertices);
		Graph G;
		if (numberOfVertices == 0){
			System.out.println("processing GraphAdjListImpl - " + numberOfVertices);
			G = FileIO.readDataAndCreateGraph(path, new GraphAdjListImpl());
		}
		else {
			System.out.println("processing GraphAdjMatrixImpl - " + numberOfVertices);
			G = FileIO.readDataAndCreateGraph(path, new GraphAdjMatrixImpl(numberOfVertices));
		}*/
		Graph G;
		int numberOfVertices = 9;
		System.out.println("processing GraphAdjMatrixImpl - " + numberOfVertices);
		G = FileIO.readDataAndCreateGraph(path, new GraphAdjMatrixImpl(numberOfVertices));
		G.printAllVertexes();
		G.printGraph();
		// G.setGraphColoringTechnique(new GraphColoringBackTracking());
		// G.setGraphColoringTechnique(new GraphColoringGreedy());
		G.setGraphColoringTechnique(new GraphColoringWelshPowell());
		G.getGraphColoringObj().toggleShuffle();
		G.colorGraph();
		Colors.printColors(G);
		
		
		G.resetColors();
		
		for (int i = 1; i< 5; i++){
			if (G.isGraphColorable(i)){
				System.out.println("The Graph was fully colorable with number of colors = " + i);
			}
			else{
				System.out.println("The Graph was not fully colorable with number of colors = " + i);
			}
			Colors.printVertexCoverage(G, i);
			G.resetColors();
		}
		
		
//		G.setGraphColoringTechnique(new GraphColoringGreedyDFS());
//		G.colorGraph();
//		Colors.printColors(G);
//		
//		G.resetColors();
//		
//		G.setGraphColoringTechnique(new GraphColoringGreedyBFS());
//		G.colorGraph();
//		Colors.printColors(G);
	}
	
	
}
