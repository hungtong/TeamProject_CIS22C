import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// build graph
	      Graph<String> myGraph1 = new Graph<String>();
	      myGraph1.addEdge("A", "B", 0);   myGraph1.addEdge("A", "C", 0);  myGraph1.addEdge("A", "D", 0);
	      myGraph1.addEdge("B", "E", 0);   myGraph1.addEdge("B", "F", 0);
	      myGraph1.addEdge("C", "G", 0);
	      myGraph1.addEdge("D", "H", 0);   myGraph1.addEdge("D", "I", 0);
	      myGraph1.addEdge("F", "J", 0);
	      myGraph1.addEdge("G", "K", 0);   myGraph1.addEdge("G", "L", 0);
	      myGraph1.addEdge("H", "M", 0);   myGraph1.addEdge("H", "N", 0);
	      myGraph1.addEdge("I", "N", 0);

	      myGraph1.saveAsTextFile(new PrintWriter(new File("euler.txt")));
	
		/*	GraphGenerator frame = new GraphGenerator();

		frame.setSize(1000,1000);
		frame.setVisible(true);
		
		// Play with these line to draw graph, we will need name, x, y coordinate for parameter of drawVertex. 
		// With connect, which vertex and cost
		frame.drawVertex("Davis", 50,50);
		frame.drawVertex("San Jose", 100,100);
		frame.drawVertex("Berkeley", 100,200);
		frame.drawVertex("San Francisco", 200, 130);
		

		frame.drawVertex("Berkeley", 100,500);
		frame.connect(0, 1, 100);
		frame.connect(0, 2, 123);

		
		frame.drawGraph(); */
	}
}
