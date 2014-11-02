import java.util.ArrayList;
import java.util.List;

public class Automat {
	private List<Stanje> stanja = new ArrayList<Stanje>();

	public Automat(Stanje pocetnoStanje) {

		dodajStanje(pocetnoStanje);
	}

	public void dodajStanje(Stanje novoStanje) {
		stanja.add(novoStanje);

	}

}
