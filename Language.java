package world;

public class Language {

	private String theCountryCode;
	private String langSpoken; ////Ken named it name
	private boolean isOfficial; 
	private float percentage;
	
	//More straight forward approach as oppose to switch statements
	public Language(String input) {
		String[] values = input.split("\t");
		theCountryCode = values[0];
		langSpoken = values[1];
		isOfficial = values[2].charAt(0) == 'T';
		percentage = Float.parseFloat(values[3]);	
	}
	
	
	public String getTheCountryCode() {
		return theCountryCode;
	}
	
	public String getLangSpoken() {
		return langSpoken;
	}
	
	public boolean isOfficial() {
		return isOfficial;
	}
	
	public float getPerc() {
		return percentage;
	}
	
	@Override //
	public String toString() {
		return langSpoken; 
	}
	
}
