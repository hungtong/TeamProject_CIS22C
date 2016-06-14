public class LocationVisitor implements Visitor<Location> {
	private int lineBreakSignal = 0;
	
	public void resetLineBreakSignal() {
		lineBreakSignal = 0;
	}
	
	@Override
	public void visit(Location location) {
		if (lineBreakSignal % 5 == 0) 
			   System.out.print("\n\t");
		
		lineBreakSignal++;
		System.out.print(location.getName() + " | ");
	}
	
}
