package world;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountryTest {

	public static void main(String[] args) throws IOException {
		//Called method as (ArrayList<Country> countries) parameters.
		//writeToFile(loadCountries());
		browseCountries();
		
	//  This entire commented code was our past experiments of sorting 	
	/*	ArrayList<Country> countries = loadCountries();
		for(Country c : countries)System.out.println(c); */
	/*	ArrayList<City> cities = loadCities2();
		City[] aCities = cities.toArray(new City[0]); //cities becomes array
		System.out.println(areCitiesSorted(aCities));
		Arrays.sort(aCities); //Requires that city uses that interface. -----> Or it wouldn't know what to sort.
		System.out.println(areCitiesSorted(aCities));
		//Here in lines 32-33, we did some custom sorting.  
	 * 	Comparator<City> ccc = aCities[0].createComparator(
				CitySortBy.POPULATION, true);
		Arrays.sort(aCities, ccc);
		for(int i=0;i<aCities.length;++i)
			System.out.println(aCities[i].getName()); */
	}
	
	public static void browseCountries() throws IOException {
		ArrayList<Country> countries = loadCountries();
		//Third test, sort and binary search
		countries.sort((cx, cy) -> cx.getCode().compareTo(cy.getCode()));
		//Second test after linear search, Mapping using HashMap. Chose over TreeMap as it is a slight bit faster.
		//When we get to new Hashmap - (countries.size() represents capacity of countries. In this case, 239. If 
		//left blank, that would be a default constructor of Hashmap with a capacity of 16. 
		HashMap<String,Country> countryMap = new HashMap<String,Country>(countries.size());
		for(Country c : countries) countryMap.put(c.getCode(), c); //<-- Building the map
		Scanner input = new Scanner(System.in);	
		while(true) {
			outputCountryCodes(countries);
			System.out.print("Please enter a country code: ");
			String cc = input.nextLine();
			//test for null first. short-circuiting. if done the other way, NullPointerException.
			if(cc == null || cc.length() == 0) break;
			cc = cc.toUpperCase(); //without this, if user inputted lower case codes, would get what they want
			Country tempCountry = new Country(cc); //We used this tempCountry to gather results solely for our comparator.
			@SuppressWarnings("unused")
			int index = Collections.binarySearch(countries, tempCountry, (cx, cy) -> cx.getCode().compareTo(cy.getCode()));
			if(index >= 0) {
				System.out.println(countries.get(index).description()); //countries.get(index) = real country object, 
																		//unlike tempCountry
			} else {
				System.out.println("Sorry - that country code was not found. ");
			}
			/*if(countryMap.containsKey(cc)) {
				System.out.println(countryMap.get(cc).description());
			} else {
				System.out.println("Sorry - that country code was not found.");
			} */
			System.out.println();
			//Testing all three ways to get countries. First, linear search as seen below
	      /*  for(Country c : countries) {
				if(cc.equals(c.getCode())) {
					System.out.println(c.description());
					System.out.println();
					break; */
			//Despite performance, couple of things could have wrong here ^. 
			//casing problems, and long last executing without values is valid, but not friendly
		  //}
			  //}
		}
	}
	
	private static void outputCountryCodes(ArrayList<Country> countries) {
		// Write all country codes to the console, 10 per line. 
		int nWritten = 0;
		for(Country c : countries) {
			System.out.printf("%s ", c.getCode());
			if(++nWritten % 10 == 0) System.out.println();
		}
		System.out.println();
	}
	
	private static boolean areCitiesSorted(City[] cities) {
		for(int i=1;i<cities.length;++i) {
			if (cities[i-1].getPopulation() > cities[i].getPopulation())
				return false;
		}
		return true;
	}
	
	private static ArrayList<Country> loadCountries() throws IOException {
		ArrayList<City> cities = loadCities2();
		//Using HashMap since we don't care for the order here. 
		HashMap<Integer,City> cityMap = new HashMap<Integer,City>();
		for(City c: cities)
		//Could use getId as key since we know its coming from a DB, its unique
			cityMap.put(c.getId(), c); //put in TreeMap a little slower
		Map<String, ArrayList<Language>> languages = loadLanguages();
		ArrayList<Country> countries = new ArrayList<Country>();
		for(String line: readAllLinesExceptFirst("countries.csv")) {
			Country c = new Country(line, cityMap);
			c.setLanguages(languages);
			countries.add(c);
			System.out.println(c);
		}
		System.out.println("Read " + countries.size() + " countries.");
		return countries;
	}
	
	private static ArrayList<City> loadCities2() throws IOException {
		ArrayList<City> cities = new ArrayList<City>();
		for(String line: readAllLinesExceptFirst("cities.csv")) {
			City c = new City(line);
			cities.add(c);
		}
		System.out.println("Read " + cities + " cities.");
		return cities;
	}
	
	private static Map<String, ArrayList<Language>> loadLanguages() 
			throws IOException {
		//Key is String of countryCode here, value ArrayList of language
		//Used ArrayList as value, since some countryCodes aren't unique.
		//Logic here is a bit different since similarites occur in languages.
		//So now ABW, can represent a value of 4
		Map<String,ArrayList<Language>> map = new HashMap<String,ArrayList<Language>>();
		for(String line : readAllLinesExceptFirst("languages.csv")) {
			Language lang = new Language(line);
			if(map.containsKey(lang.getTheCountryCode())) {
				map.get(lang.getTheCountryCode()).add(lang);
			} else {
				//Generate a new ArrayList for common values
				//Example: code ABW would be key, 4 would be value
				ArrayList<Language> a = new ArrayList<Language>();
				a.add(lang);
				map.put(lang.getTheCountryCode(), a);
			}
		}
		return map;
	}
	
	private static ArrayList<String> readAllLinesExceptFirst(String fpath)
		throws IOException {
	//Method is used to ignore that header line
	FileReader fileRdr = new FileReader(fpath);
	BufferedReader bRdr = new BufferedReader(fileRdr);
	try {
	ArrayList<String> values = new ArrayList<String>();
	//Notice String header is gone. Bottom line reads the same
	bRdr.readLine();
	while(true) {
		String line = bRdr.readLine();
		if (line == null || line.length()==0) break;
		values.add(line);
	}
	return values;
	}
	finally { //Unlike catch, whether it throws an exception
		      //this close will execute.
		bRdr.close();
	}
		
	}
	
	private static void writeToFile(ArrayList<Country> countries) 
			throws IOException {
		FileWriter writer = new FileWriter("newCountries.txt");
		BufferedWriter bWriter = new BufferedWriter(writer);
		for(Country c : countries) {
			bWriter.write(c.toString());
			bWriter.newLine(); // --> Puts all the countries on a new line.
		}
		bWriter.flush(); //--> push everything in your buffer into the file. Buffer time can vary
		bWriter.close(); //--> closes the stream
	}
}
