
public class Driver {
	public static void main(String[] args) {
		GraphGenerator frame = new GraphGenerator();

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

		
		frame.drawGraph();
	}
}
