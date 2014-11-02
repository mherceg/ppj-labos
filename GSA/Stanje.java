import java.util.ArrayList;
import java.util.List;


public class Stanje {
	private String imeStanja;
	private List<Prijelaz> listaPrijelaza = new ArrayList<Prijelaz>();
	
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


	public List<Prijelaz> getListaPrijelaza() {
		if(listaPrijelaza.isEmpty()){
			return null;
		}
		else{
			return listaPrijelaza;
		}
		
	}

	
	
}
