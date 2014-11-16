import java.util.ArrayList;
import java.util.List;

public class Automat {
	private List<Stanje> stanja;
	private Stanje pocetnoStanje;

	private List<String> znakovi;

	public Automat() {
		stanja = new ArrayList<Stanje>();
		znakovi = new ArrayList<String>();
	}

	public void dodajStanje(Stanje novoStanje) {
		stanja.add(novoStanje);
		
		for(String znak :novoStanje.getZnakoviPrijelaza()){
			if(!znakovi.contains(znak)){
				znakovi.add(znak);
			}
		}

	}
	public List<String>getZnakoviPrijelaza(){
		return znakovi;
	}

	public Stanje getPocetnoStanje() {
		return pocetnoStanje;
	}

	public void setPocetnoStanje(Stanje pocetnoStanje) {
		for(String znak :pocetnoStanje.getZnakoviPrijelaza()){
			if(!znakovi.contains(znak)){
				znakovi.add(znak);
			}
		}
		this.pocetnoStanje = pocetnoStanje;
	}

	public List<Stanje> getStanja() {
		return stanja;
	}

}
