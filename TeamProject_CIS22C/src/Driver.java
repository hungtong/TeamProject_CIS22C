import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {	
	private static Scanner userScanner = new Scanner(System.in);
	
	private static HashMap<String, Location> availableLocations;
	private static PossibleEulerCircuit<Location> possibleEulerCircuit;
	
		
	public static void main(String[] args) throws IOException {
		
		possibleEulerCircuit = new PossibleEulerCircuit<>();
		availableLocations = readInputFile();
		
		saveCircuitAsTextFile();
	//	navigateOption(showMenu());
		
				   
		/*
		HashMap<Location, Vertex<Location> > allLocations = possibleEulerCircuit.vertexSet;
		Location currentLocation = availableLocations.get("UPS");
		if (currentLocation != null)
			possibleEulerCircuit.findEulerCircuit(allLocations.get(currentLocation));
		else System.out.println("\tThis location is not available in the graph!");	*/

		possibleEulerCircuit.showAdjTable(); 

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
			System.out.println("\t-----------------------------------------------------");
			
			System.out.println("\tType A Number To Go To The Option");
			System.out.println("\t\t1. Add Edge");
			System.out.println("\t\t2. Remove Edge");
			System.out.println("\t\t3. Undo Removal");
			System.out.println("\t\t4. Diplay Graph");
			System.out.println("\t\t5. Solve Problem");
			System.out.println("\t\t6. Save as Text File");
			System.out.println("\t\t7. Exit");
			
			System.out.println();
			
			System.out.print("\tYour Choice: ");
			
		//	@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			
			try {
				int userChoice = input.nextInt();
				if (userChoice < 1 || userChoice > 7) 
					System.out.println("\n\tInvalid Number! Please Type Another Number.");
				else {
					input.close();
					return userChoice;
				}
			}
			catch (InputMismatchException exception) {
				trialLimit--;
				if (trialLimit == 0) {
					System.out.println("\n\tYou Have Exceeded Invalid Input Trial Limits!");
					System.out.println("\tThe Program Is Forced To End");
					System.exit(0);
				}
				System.out.println("\n\tInvalid Input! You Can Type Again " + trialLimit +" times.");
				System.in.read();
			}
									
		} 	
	}
	
	public static void navigateOption(int option) {
		switch (option) {
	        case 1:  
	        	addingEdge();
	            break;
	        case 2:  
	        	        	
	        	break;
	        case 3:  
	        	
	        	break;
	        case 4:  
	        	
	            break;
	        case 5:  
	        	
	        	break;
	        case 6:  
	        	saveCircuitAsTextFile();
	        	break;
	        case 7:  
	        
	        	break;
		}
	}
	
	public static void addingEdge() {
		Scanner input = new Scanner(System.in);
		
		System.out.print("\n\tEnter Location 1: ");
		String location1 = input.nextLine();
		if (!availableLocations.containsKey(location1)) {
			System.out.println("\tFailed To Add Edge.\n" + location1 + " Is Not In The Graph.");
			input.close();
			return;
		}
		
		System.out.print("\n\tEnter Location 2: ");
		String location2 = input.nextLine();
		if (!availableLocations.containsKey(location1)) {
			System.out.println("\tFailed To Add Edge.\n" + location2 + " Is Not In The Graph.");
			input.close();
			return;
		}
			
		Location source = availableLocations.get(location1);
		Location destination = availableLocations.get(location2);
		
		possibleEulerCircuit.addEdge(source, destination, 0);
		System.out.println("\tSuccessfully Added Edges Between " + location1 + " And " + location2);
		
		input.close();
	}
	
	public static void saveCircuitAsTextFile() {
		String filename;
        Scanner scanner = null;
        
		System.out.print("\tEnter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);
    	
    	try {
    		possibleEulerCircuit.saveAsTextFile(new PrintWriter(file));
    	}
    	catch (IOException exception) {
    		
    	}
    	finally {
    		if (scanner != null) 
    			scanner.close();
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
