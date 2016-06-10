public class Location {
	private String name;
	
	public Location(String name) {
		setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object anotherLocation) {
		boolean isLocation = anotherLocation instanceof Location;
		if (!isLocation)
			return false;
		return name.equals(((Location) anotherLocation).getName());
	}
	
	@Override
	public int hashCode() {
		int returnValue = 0;

	    for(int k = 0; k < name.length(); k++ )
	    	returnValue = 37 * returnValue + name.charAt(k);

	    return returnValue;
	}
}
