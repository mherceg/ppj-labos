import java.util.ArrayList;


public class Stanje {
	private String imeStanja;
	private ArrayList<Prijelaz> listaPrijelaza = new ArrayList<Prijelaz>();
	
	public Stanje(String imeStanja) {
		super();
		this.imeStanja = imeStanja;
	}

	
	public void dodajPrijelaz(Prijelaz novi){
		listaPrijelaza.add(novi);		
	}


	public String getImeStanja() {
		return imeStanja;
	}


	public void setImeStanja(String imeStanja) {
		this.imeStanja = imeStanja;
	}


	public ArrayList<Prijelaz> getListaPrijelaza() {
		if(listaPrijelaza.isEmpty()){
			return null;
		}
		else{
			return listaPrijelaza;
		}
		
	}

	
	
}
