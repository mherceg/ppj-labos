import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutomatonTranformator {

	public Automat eNkaToNka(Automat eNka) {
		Automat nka = new Automat();

		List<Stanje> staroPocetnoStanje = new ArrayList<Stanje>();
		staroPocetnoStanje.add(eNka.getPocetnoStanje());

		Stanje novoPocetnoStanje = new Stanje(staroPocetnoStanje.get(0)
				.getImeStanja());

		List<String> znakoviPrijelaza = eNka.getPocetnoStanje()
				.getZnakoviPrijelaza();

		for (String znak : znakoviPrijelaza) {
			List<Stanje> novaStanja = prijelazKapica(staroPocetnoStanje, znak);

			for (Stanje novoStanje : novaStanja) {
				novoPocetnoStanje.dodajPrijelaz(new Prijelaz(znak, novoStanje));
			}
		}

		return nka;

	}

	private List<Stanje> prijelaz(List<Stanje> stanja, String znak) {
		List<Stanje> novaStanja = new ArrayList<Stanje>();

		for (Stanje stanje : stanja) {
			for (Prijelaz prijelaz : stanje.getListaPrijelaza()) {
				if (prijelaz.getZnak().equals(znak)) {
					if (!novaStanja.contains(prijelaz.getNovoStanje())) {
						novaStanja.add(prijelaz.getNovoStanje());
					}
				}
			}
		}
		return novaStanja;

	}

	private List<Stanje> prijelazKapica(List<Stanje> staroStanje, String znak) {
		List<Stanje> novaStanja = new ArrayList<Stanje>();

		novaStanja = getEpsilonOkruzenje(staroStanje);
		List<Stanje> novaStanjaNakonPrijelaz = prijelaz(novaStanja, znak);

		return getEpsilonOkruzenje(novaStanjaNakonPrijelaz);

	}

	public List<Stanje> getEpsilonOkruzenje(List<Stanje> stanja) {

		List<Stanje> okruzenje = new ArrayList<Stanje>();
		for (Stanje stanje : stanja) {
			calculateEpsilonOkruzenje(stanje, okruzenje);
		}
		return okruzenje;
	}

	private void calculateEpsilonOkruzenje(Stanje stanje, List<Stanje> okruzenje) {

		if (!okruzenje.contains(stanje)) {
			okruzenje.add(stanje);
		}

		List<Stanje> novaStanja = new ArrayList<Stanje>();

		for (Prijelaz prijelaz : stanje.getListaPrijelaza()) {

			Stanje novoStanje = prijelaz.getNovoStanje();

			if (prijelaz.getZnak().equals("$")) {
				if (!okruzenje.contains(novoStanje)
						&& !novaStanja.contains(novoStanje)) {
					novaStanja.add(novoStanje);
				}
			}
		}

		for (Stanje novoStanje : novaStanja) {
			calculateEpsilonOkruzenje(novoStanje, okruzenje);
		}

	}

}
