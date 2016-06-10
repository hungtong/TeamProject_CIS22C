import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {	
	private static Scanner userScanner = new Scanner(System.in);
		
	public static void main(String[] args) {
		Scanner inputFile = openInputFile();
		   
		   if (inputFile == null) {
			   System.out.println("Program has been crashed due to invalid input file.\n" +
					   "Please run the program again and enter a valid input file.");
			   System.exit(0);
		   }
			  
		   
		   PossibleEulerCircuit<Location> possibleEulerCircuit = new PossibleEulerCircuit<>(new Visitor<Location>() {
				@Override
				public void visit(Location object) {
					System.out.print(object.toString() + " ");
				}
			 });
		   
		   HashMap<Integer, Location> vertexSet = new HashMap<>();
		   while (inputFile.hasNext()) {
			   String[] vertices = inputFile.nextLine().split("[,_~]");

			   Location currentLocation = vertexSet.get(hashString(vertices[0]));
			   if (currentLocation == null)
				   currentLocation = new Location(vertices[0]);
			   
			   for (int i = 1; i < vertices.length; i++) {
				   Location adjacentLocation = vertexSet.get(hashString(vertices[i]));
				   if (adjacentLocation == null)
					   adjacentLocation = new Location(vertices[i]);
				   possibleEulerCircuit.addEdge(currentLocation, adjacentLocation);
			   }
		   }
		   
		   possibleEulerCircuit.showAdjTable();
		   System.out.println("\n");
		   HashMap<Location, Vertex<Location> > ver = possibleEulerCircuit.vertexSet;
		   possibleEulerCircuit.findEulerCircuit(ver.get("San Luis Obispo"));
		   System.out.println("\n");
		   possibleEulerCircuit.findEulerCircuit(ver.get("Los Angeles"));
		   System.out.println("\n");
		   possibleEulerCircuit.findEulerCircuit(ver.get("Davis"));
	}
	
	private static int hashString(String key) {
		int returnValue = 0;

	    for(int k = 0; k < key.length(); k++ )
	    	returnValue = 37 * returnValue + key.charAt(k);

	    return returnValue;
	}
	
	 /**
	   * Open an input file to read
	   * @return a Scanner to read input file
	 */
	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
        	File file= new File(filename);

        	try{
        		scanner = new Scanner(file);
        	}// end try
        	catch(FileNotFoundException fe){
        	   System.out.println("Can't open input file\n");
       	    return null; // array of 0 elements
        	} // end catch
        	return scanner;
	}
	   
	   public static void readInputFile() {
		   Scanner inputFile = openInputFile();
		   
		   if (inputFile == null) {
			   System.out.println("Program has been crashed due to invalid input file.\n" +
					   "Please run the program again and enter a valid input file.");
			   System.exit(0);
		   }
			  
		   
		   PossibleEulerCircuit<Location> possibleEulerCircuit = new PossibleEulerCircuit<>(new Visitor<Location>() {
				@Override
				public void visit(Location object) {
					System.out.print(object.toString() + " ");
				}
			 });
		   
		   HashMap<String, Location> vertexSet = new HashMap<>();
		   while (inputFile.hasNext()) {
			   String[] vertices = inputFile.nextLine().split("[,_~]");

			   Location currentLocation = vertexSet.get(vertices[0]);
			   if (currentLocation == null)
				   currentLocation = new Location(vertices[0]);
			   
			   for (int i = 1; i < vertices.length; i++) {
				   Location adjacentLocation = vertexSet.get(vertices[i]);
				   if (adjacentLocation == null)
					   adjacentLocation = new Location(vertices[0]);
				   possibleEulerCircuit.addEdge(currentLocation, adjacentLocation);
			   }
		   }
		   
		   HashMap<Location, Vertex<Location> > vertices = possibleEulerCircuit.vertexSet;
		   possibleEulerCircuit.findEulerCircuit(vertices.get("San Luis Obispo"));
		   System.out.println("\n");
		   possibleEulerCircuit.findEulerCircuit(vertices.get("Los Angeles"));
		   System.out.println("\n");
		   possibleEulerCircuit.findEulerCircuit(vertices.get("Davis"));
	   }
}

/*
		public static void main(String[] args) throws FileNotFoundException, IOException {
		 PossibleEulerCircuit<String> possibleEulerCircuit = new PossibleEulerCircuit<>(new Visitor<String>() {
			@Override
			public void visit(String object) {
				
			}
		 });
		 
		 possibleEulerCircuit.addEdge("A", "B");
		 possibleEulerCircuit.addEdge("A", "F");
		 
		 possibleEulerCircuit.addEdge("B", "A");
		 possibleEulerCircuit.addEdge("B", "C");
		 possibleEulerCircuit.addEdge("B", "D");
		 possibleEulerCircuit.addEdge("B", "E");
		 
		 possibleEulerCircuit.addEdge("C", "B");
		 possibleEulerCircuit.addEdge("C", "D");
		 possibleEulerCircuit.addEdge("C", "E");
		 possibleEulerCircuit.addEdge("C", "F");
		 
		 possibleEulerCircuit.addEdge("D", "B");
		 possibleEulerCircuit.addEdge("D", "C");
		 possibleEulerCircuit.addEdge("D", "E");
		 possibleEulerCircuit.addEdge("D", "F");
		 
		 
		 possibleEulerCircuit.addEdge("E", "B");
		 possibleEulerCircuit.addEdge("E", "C");
		 possibleEulerCircuit.addEdge("E", "D");
		 possibleEulerCircuit.addEdge("E", "F");
		 
		 possibleEulerCircuit.addEdge("F", "A");
		 possibleEulerCircuit.addEdge("F", "C");
		 possibleEulerCircuit.addEdge("F", "E");
		 possibleEulerCircuit.addEdge("F", "D");
		 
		 possibleEulerCircuit.showAdjTable();
		 System.out.println("\n");
		 possibleEulerCircuit.findEulerCircuit(possibleEulerCircuit.vertexSet.get("F"));
		 System.out.println("\n");
		 possibleEulerCircuit.findEulerCircuit(possibleEulerCircuit.vertexSet.get("A"));
		 System.out.println("\n");
		 possibleEulerCircuit.showAdjTable();
		
	}
*/
