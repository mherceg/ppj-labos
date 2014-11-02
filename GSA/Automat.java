import java.util.ArrayList;
import java.util.List;

public class Automat {
	private List<Stanje> stanja = new ArrayList<Stanje>();
	private Stanje pocetnoStanje;

	
	
	public void dodajStanje(Stanje novoStanje) {
		stanja.add(novoStanje);

	}

	public Stanje getPocetnoStanje() {
		return pocetnoStanje;
	}

	public void setPocetnoStanje(Stanje pocetnoStanje) {
		this.pocetnoStanje = pocetnoStanje;
	}

	public List<Stanje> getStanja() {
		return stanja;
	}
	
	

}
