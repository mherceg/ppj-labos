import java.util.ArrayList;
import java.util.List;

public class eNKA {
	private List<Stanje> stanja = new ArrayList<Stanje>();

	public eNKA(Stanje pocetnoStanje) {

		dodajStanje(pocetnoStanje);
	}

	public void dodajStanje(Stanje novoStanje) {
		stanja.add(novoStanje);

	}

}
