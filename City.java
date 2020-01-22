package world;

import java.util.Comparator;

public class City implements Comparable<City> { //implementing a interface called Comparable. 
	//Comparable provides natural sorting for the class. Used the method compareTo down below from Comparable. 
	private int id;
	private String name;
	private String countryCode;
	private String district;
	private int population;
	
	// Construct a city from an input string of the form:
	// 1	Kabul	AFG	Kabol	1780000
	public City(String input) {
		String[] values = input.split("\t"); //tab character represents the tabs of the table above. Line 14. 
		for(int i=0;i<values.length;++i) {
			//Here we are switching on an int. Optional choice we chose. 
			switch(i) {
				case 0: id = Integer.parseInt(values[i]); break;
				case 1: name = values[i]; break;
				case 2: countryCode = values[i]; break;
				case 3: district = values[i]; break;
				case 4: 
					population = Integer.parseInt(values[i]);
					break;	
			}
		}
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public String getDistrict() {
		return district;
	}
	public int getPopulation() {
		return population;
	}
	
	@Override
	//returning toString can only be used once. String.Format could give flexibility in providing multiple args. \r\n  
	public String toString() {
		//return name; 
		//Going to try with String.Format if we wanted. Like we did in Book class of Library. 
		return String.format("ID: %d\r\nName: %s\r\nPopulation: %d", id, name, population);
	}

	@Override //This is used for sorting
	//We override this because we are implementing this method to use Comparable. It would still work without keyword.
	public int compareTo(City o) { 
		if (population > o.population) return 1; //Above 
		if (population < o.population) return -1; //Below
		return 0; //Equal 
	}
	
	//Disregard the line below all the way to the last. That comparator is used for custom sorting which we are not doing.
	public Comparator<City> createComparator
	(CitySortBy sortBy, boolean reverse) {
		return new CityComparator(sortBy, reverse);
		//Notice return type is comparator and see that it returns a new CityComparator object(sortBy, reverse)
	}
		
	//Nested class below. Has a constructor and method.
	private class CityComparator implements Comparator<City> {
		private CitySortBy sortBy;
		private boolean reverse;
		
		public CityComparator(CitySortBy sortBy, boolean reverse) {
			this.sortBy = sortBy;
			this.reverse = reverse;
		}
		
		@Override
		public int compare(City o1, City o2) {
			int result = 0;
			//Here we are switching on an enum
			switch(sortBy) {
			case ID:
				if (o1.id > o2.id) result = 1; else
					if (o1.id < o2.id) result = -1; else
						result = 0;
				break;
			case NAME:
				result = o1.name.compareTo(o2.name);
				break;
			case COUNTRYCODE:
				result = o1.countryCode.compareTo(o2.countryCode);
				break;
			case DISTRICT:
				result = o1.district.compareTo(o2.district);
				break;
			case POPULATION:
				if (o1.population > o2.population) result = 1; else
					if (o1.population < o2.population) result = -1; else
						result = 0;
				break;					
			}
			return reverse ? -result : result;
		}
		
	}
}
