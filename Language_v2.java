package world;

public class Language_v2 {

	private int id; 
	private String theCountryCode;
	private String langSpoken; 
	private boolean isOfficial; 
	private float perc;
	
	public Language_v2(String input) {
		String[] values = input.split("\t");
		for(int i=0;i<values.length;++i) {
			switch(i) {
				case 0: id = Integer.parseInt(values[i]); break;
				case 1: theCountryCode = values[i]; break;
				case 2: langSpoken = values[i]; break;
				case 3: isOfficial = Boolean.parseBoolean(values[i]); break;
				case 4: perc = Float.parseFloat(values[i]); break;	
			}
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTheCountryCode() {
		return theCountryCode;
	}
	public void setTheCountryCode(String theCountryCode) {
		this.theCountryCode = theCountryCode;
	}
	public String getLangSpoken() {
		return langSpoken;
	}
	public void setLangSpoken(String langSpoken) {
		this.langSpoken = langSpoken;
	}
	public boolean isOfficial() {
		return isOfficial;
	}
	public void setOfficial(boolean isOfficial) {
		this.isOfficial = isOfficial;
	}
	public float getPerc() {
		return perc;
	}
	public void setPerc(float perc) {
		this.perc = perc;
	} 
	
}
