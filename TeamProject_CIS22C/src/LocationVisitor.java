public class LocationVisitor implements Visitor<Location> {
	
	@Override
	public void visit(Location location) {
		System.out.print(location.getName() + ",");
	}
	
}
