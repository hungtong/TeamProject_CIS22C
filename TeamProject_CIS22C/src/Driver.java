import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {	
	private static Scanner userScanner = new Scanner(System.in);
		
	public static void main(String[] args) {
		     
	
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
