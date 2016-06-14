import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;

public class Driver {	
	private static Scanner userScanner = new Scanner(System.in);
	
	private static HashMap<String, Location> availableLocations;
	private static PossibleEulerCircuit<Location> possibleEulerCircuit;
	private static Visitor<Location> locationVisitor = new LocationVisitor();
		
	public static void main(String[] args) throws IOException {
		
		possibleEulerCircuit = new PossibleEulerCircuit<>();
		availableLocations = readInputFile();

		int userChoice = 0;
		while (userChoice != 9) {
			userChoice = showMenu();
			navigateOption(userChoice);
			System.in.read();
		}
			   		
	}
	
	public static HashMap<String, Location> readInputFile() {
		Scanner inputFile = openInputFile();
		
		if (inputFile == null) {
			System.out.println("\tUnable To Open Input File");
			System.exit(0);
		}
		System.out.println("\tSuccessfully Opened Input File");
		
		if (inputFile.hasNext() == false) {
			System.out.println("\tInput File Is Empty");
			System.exit(0);
		}
		
		HashMap<String, Location> availableLocations = new HashMap<>();
		Location currentLocation;
		
		while (inputFile.hasNext()) {
	    	String[] vertices = inputFile.nextLine().split("[,]");

	    	currentLocation = availableLocations.get(vertices[0]);
	    	if (currentLocation == null) {
	    		currentLocation = new Location(vertices[0]);
	    		availableLocations.put(vertices[0], currentLocation);
	    	}
				
			   
			for (int i = 1; i < vertices.length; i++) {
				Location adjacentLocation = availableLocations.get(vertices[i]);
		    	if (adjacentLocation == null) {
		    		adjacentLocation = new Location(vertices[i]);
		    		availableLocations.put(vertices[i], adjacentLocation);
		    	}
			    possibleEulerCircuit.addEdge(currentLocation, adjacentLocation, 0);
		    }
		}
		
		return availableLocations;
	}
		
	public static int showMenu() throws IOException {		
		int trialLimit = 3;
		
		for (;;) {
			System.out.println("\n\t-----------------------------------------------------");
			
			System.out.println("\tType A Number To Go To The Option");
			System.out.println("\t\t1. Add Edge");
			System.out.println("\t\t2. Remove Edge");
			System.out.println("\t\t3. Undo Removal");
			System.out.println("\t\t4. Diplay Graph");
			System.out.println("\t\t5. Depth First Traversal");
			System.out.println("\t\t6. Breadth First Traversal");
			System.out.println("\t\t7. Find Euler Circuit");
			System.out.println("\t\t8. Save as Text File");
			System.out.println("\t\t9. Exit");
			
			System.out.println();
			
			System.out.print("\tYour Choice: ");
			
			userScanner = new Scanner(System.in);
			try {
				int userChoice = userScanner.nextInt();
				
				
				if (userChoice < 1 || userChoice > 9) 
					System.out.println("\n\tInvalid Number! Please Type Another Number.");
				else 
					return userChoice;
			}
			catch (InputMismatchException exception) {
				trialLimit--;
				if (trialLimit == 0) {
					System.out.println("\n\tYou Have Exceeded Invalid Input Trial Limits!");
					System.out.println("\tThe Program Is Forced To End");
					System.exit(0);
				}
				System.out.println("\n\tInvalid Input! You Can Type Again " + trialLimit +" Times.");
				System.in.read();
			}							
		} 	
	}
	
	public static void navigateOption(int option) throws IOException {
		switch (option) {
	        case 1:  
	        	addingEdge();
	        	break;
	        case 2:  
	        	removingEdge(); 
	        	break;
	        case 3:  
	        	undoRemovingEdge();
	        	break;
	        case 4:  
	        	displayingGraph();
	        	break;
	        case 5:  
	        	depthFirstTraversal();
	        	break;
	        case 6:
	        	breadthFirstTraversal();
	        	break;
	        case 7:  
	        	findingEulerCircuit();
	        	break;
	        case 8:  
	        	saveCircuitAsTextFile();
	        	break;
	        case 9:  
	        	System.out.println("\tThank You For Using The Program");
	        	break;
		}
	}
	
	public static void addingEdge() {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tAdd Edge\n");
		
		userScanner.nextLine();
		
		System.out.print("\n\tEnter Location 1: ");
		String location1 = userScanner.nextLine();
		
		if (!availableLocations.containsKey(location1)) {
			System.out.println("\n\tFailed To Add Edge.\n\t\"" + location1 + "\" Is Not In The Graph.");
			return;
		}
		
		System.out.print("\n\tEnter Location 2: ");
		String location2 = userScanner.nextLine();
		
		if (!availableLocations.containsKey(location2)) {
			System.out.println("\n\tFailed To Add Edge.\n\t\"" + location2 + "\" Is Not In The Graph.");
			return;
		}
			
		Location source = availableLocations.get(location1);
		Location destination = availableLocations.get(location2);
		
		possibleEulerCircuit.addEdge(source, destination, 0);
		System.out.println("\n\tSuccessfully Added Edges Between \"" + location1 + "\" And \"" + location2 + "\"");
	}
	
	public static void removingEdge() {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tRemove Edge\n");
		
		userScanner.nextLine();
		
		System.out.print("\n\tEnter Location 1: ");
		String location1 = userScanner.nextLine();
		
		if (!availableLocations.containsKey(location1)) {
			System.out.println("\tFailed To Add Edge.\n\t\"" + location1 + "\" Is Not In The Graph.");
			return;
		}
		
		System.out.print("\n\tEnter Location 2: ");
		String location2 = userScanner.nextLine();
		
		if (!availableLocations.containsKey(location2)) {
			System.out.println("\tFailed To Add Edge.\n\t\"" + location2 + "\" Is Not In The Graph.");
			return;
		}
			
		Location source = availableLocations.get(location1);
		Location destination = availableLocations.get(location2);
		
		if (possibleEulerCircuit.remove(source, destination))
			System.out.println("\tSuccessfully Removed Edges Between \"" + location1 + "\" And \"" + location2 + "\"");

	}
	
	public static void undoRemovingEdge() throws IOException {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tUndo Removing Edge\n");
		
		userScanner.nextLine();
		int trialLimit = 3;

		for (;;) {								
			try {
				System.out.print("\n\tEnter Number of Times To Undo: ");
				int undoTimes = userScanner.nextInt();
				possibleEulerCircuit.undoRemoval(undoTimes);
				return;
			}
			catch (InputMismatchException exception) {
				trialLimit--;
				if (trialLimit == 0) {
					System.out.println("\n\tYou Have Exceeded Invalid Input Trial Limits!");
					System.out.println("\tThe Program Is Forced To End");
					System.exit(0);
				}
				System.out.println("\n\tInvalid Input! You Can Type Again " + trialLimit +" Times.");
				System.in.read();
			}
									
		} 	
	}
	
	public static void displayingGraph() throws IOException {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tDisplay Graph\n");
					
		System.out.println("\t____ ADJACENCY LISTS ____\n");
		possibleEulerCircuit.showAdjTable();
		
	}
	
	public static void depthFirstTraversal() {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tDemonstrate Traversal\n");
		
		userScanner.nextLine();
		
		System.out.print("\tEnter Starting Location: ");
		Location startLocation = availableLocations.get(userScanner.nextLine());
		
		if (startLocation != null) {
			System.out.print("\n\t___ DEPTH FIRST TRAVERSAL ___\n");
			possibleEulerCircuit.depthFirstTraversal(startLocation, locationVisitor);
			
			((LocationVisitor) locationVisitor).resetLineBreakSignal();
		}
	}
	
	public static void breadthFirstTraversal() {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tDemonstrate Traversal\n");
		
		userScanner.nextLine();
		
		System.out.print("\tEnter Starting Location: ");
		Location startLocation = availableLocations.get(userScanner.nextLine());
		
		if (startLocation != null) {			
			System.out.print("\n\n\t___ BREADTH FIRST TRAVERSAL ___\n");
			possibleEulerCircuit.breadthFirstTraversal(startLocation, locationVisitor);
			
			((LocationVisitor) locationVisitor).resetLineBreakSignal();
		}
	}
	
	public static void findingEulerCircuit() {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tFind Euler Circuit\n");
		
		if (possibleEulerCircuit.isEulerCircuit()) {
			Iterator<Entry<Location, Vertex<Location>>> iter = possibleEulerCircuit.vertexSet.entrySet().iterator();
			
			while (iter.hasNext()) {
				Entry<Location, Vertex<Location>> entry = iter.next();
				possibleEulerCircuit.findEulerCircuit(entry.getValue());
			}
		
		}
	}
	
	public static void saveCircuitAsTextFile() {
		System.out.println("\t-----------------------------------------------------");
		System.out.println("\t\tSave Circuit\n");
		
		userScanner.nextLine();
		
		String filename;
		System.out.print("\tEnter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);
    	
    	try {
    		possibleEulerCircuit.saveAsTextFile(new PrintWriter(file));
    	}
    	catch (IOException exception) {
    		
    	}   	
	}
	
	 /**
	   * Open an input file to read
	   * @return a Scanner to read input file
	 */
	public static Scanner openInputFile() {
		String filename;
        Scanner scanner=null;
        
		System.out.print("\tEnter the input filename: ");
		
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    	   System.out.println("\tCan't open input file\n");
   	    return null; // array of 0 elements
    	} // end catch
    	return scanner;
	}
}
