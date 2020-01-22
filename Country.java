package world;

import java.util.ArrayList;
import java.util.Map;

public class Country {
	private String code;
	private String name;
	private String continent;
	private String region;
	private float surfaceArea;
	private int independenceYear;
	private int population;
	private float lifeExpectancy;
	private float gnp;
	private String localName;
	private String govtForm;
	private String headOfState;
	private int capital;
	private City city;
	//private City[] otherCities;
	private String code2;
	private Language[] languages; //Being used sb.append with nLanguages
	
	// Construct a country from an input string of the form:
	// ABW	Aruba	North America	Caribbean	193.00	NULL	103000	78.4	828.00	Aruba	Nonmetropolitan Territory of The Netherlands	Beatrix	129	AW
	//We have modified from ArrayList to Map for greater effiency
	//Gonna use city ID as keys, value will be city itself
	public Country(String input, Map<Integer,City> cities) {
		String[] values = input.split("\t");
		code = values[0];
		name = values[1];
		continent = values[2];
		region = values[3];
		surfaceArea = Float.parseFloat(values[4]);
		if ("NULL".equals(values[5])) independenceYear = -1; else
			independenceYear = Integer.parseInt(values[5]);
		population = Integer.parseInt(values[6]);
		if ("NULL".equals(values[7])) lifeExpectancy = -1; else
			lifeExpectancy = Float.parseFloat(values[7]);
		if ("NULL".equals(values[8])) gnp = -1; else
			gnp = Float.parseFloat(values[8]);
		localName = values[9];
		govtForm = values[10];
		headOfState = values[11];
		if ("NULL".equals(values[12])) capital = -1; else 
			capital = Integer.parseInt(values[12]);
		code2 = values[13];
		if (capital > 0 && cities.containsKey(capital)) {
			city = cities.get(capital); //if not, city remains null 
			
	 	/*	for(int i=0;i<cities.size();++i) {
				City c = cities.get(i);
				if (capital == c.getId()) {
					city = c;
					break;
				}
			} */
		}
	}
	
	Country(String countryCode) {
		code = countryCode;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getContinent() {
		return continent;
	}

	public String getRegion() {
		return region;
	}

	public float getSurfaceArea() {
		return surfaceArea;
	}

	public int getIndependenceYear() {
		return independenceYear;
	}

	public int getPopulation() {
		return population;
	}

	public float getLifeExpectancy() {
		return lifeExpectancy;
	}

	public float getGnp() {
		return gnp;
	}

	public String getLocalName() {
		return localName;
	}

	public String getGovtForm() {
		return govtForm;
	}

	public String getHeadOfState() {
		return headOfState;
	}

	public int getCapital() {
		return capital;
	}

	public String getCode2() {
		return code2;
	}
	
	public String description() {
	//StringBuilder good for big strings. 
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s is on the continent %S.\r\n",name, continent));
		sb.append(String.format("%s is %.1f square kilometers in area and has a population of %d. \r\n", 
				name, surfaceArea, population));
		sb.append(String.format("Our dear leader is %s, and we have an average life expectancy of %.1f. \r\n", 
				 headOfState, lifeExpectancy));
		int nLanguages = languages == null ? 0 : languages.length; //Used to ignore warning in languages field. 
																   //n meaning not
		sb.append(String.format("We speak %d languages here.", 
				 nLanguages));
		return sb.toString();
	}
	
	void setLanguages(Map<String, ArrayList<Language>> languages) { //default: can only be used within package "world"
		if (languages.containsKey(code)) //code is the field representing country code
		//This is current object. Not static. its on a instance. get(code) gets the value of code. convert to 
		//traditional array since languages is []. 
		//this object(Country).languages = languages(our map).get(get method from Map)of code, toArray(T[] a)
		//(ArrayList method) --> T would represent language in this situation. 
			this.languages = languages.get(code).toArray(new Language[0]); 
	}

	@Override
	public String toString() {
		if (city == null) return name + " (no capital)";
		return name + "(" + city.getName() + ")"; //In this specific situation, Anarticica is the only country without.
	}
}
