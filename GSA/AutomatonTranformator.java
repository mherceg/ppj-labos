import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AutomatonTranformator {

	public Automat eNkaToDka(Automat eNka) {
		Automat nka = eNkaToNka(eNka);
		System.out.println("zavrsio nka");
		return nkaToDka(nka);
	}

	private Automat eNkaToNka(Automat eNka) {

		Automat nka = new Automat();
		List<Stanje> stvorenaStanja = new ArrayList<Stanje>();
		Stanje nkaPocetno = new Stanje(eNka.getPocetnoStanje());
		nka.setPocetnoStanje(nkaPocetno);

		// stvorenaStanja.add(nkaPocetno);
		srediPrijelazeENkaNka(nkaPocetno, stvorenaStanja, eNka);

		for (Stanje stanje : stvorenaStanja) {
			nka.dodajStanje(stanje);
		}
		List<Produkcija> pocetneProdukcije = new ArrayList<Produkcija>();
		List<Stanje> prodenaStanja = new ArrayList<Stanje>();
		pocetneProdukcije = getNkaPocetnoProdukcija(eNka.getPocetnoStanje(), prodenaStanja);
		nka.getPocetnoStanje().dodajListuProdukcija(pocetneProdukcije);
		return nka;
	}

	private List<Produkcija> getNkaPocetnoProdukcija(Stanje eNkaPocetno,
			List<Stanje> prodenaStanja) {
		List<Produkcija> pocetneProdukcije = new ArrayList<Produkcija>();
		for (Prijelaz prijelaz : eNkaPocetno.getListaPrijelaza()) {
			if (prijelaz.getZnak().equals("$")) {
				if (!prodenaStanja.contains(prijelaz.getNovoStanje())) {
					prodenaStanja.add(prijelaz.getNovoStanje());
					pocetneProdukcije.add(prijelaz.getNovoStanje().getlistuProdukcija().get(0));
					
					List<Produkcija> noveProdukcije = (getNkaPocetnoProdukcija(
							prijelaz.getNovoStanje(), prodenaStanja));
					
					if (!noveProdukcije.isEmpty()) {
						for (Produkcija produkcija : noveProdukcije) {
							if (!pocetneProdukcije.contains(produkcija)) {
								pocetneProdukcije.add(produkcija);
							}
						}
					}
				}
			}
		}
		return pocetneProdukcije;
	}

	private void srediPrijelazeENkaNka(Stanje stanje,
			List<Stanje> stvorenaStanja, Automat automat) {
		List<Prijelaz> noviPrijelazi = new ArrayList<Prijelaz>();
		/*
		 * Prodi po svakom znaku za to stanje
		 */
		for (String znak : automat.getZnakoviPrijelaza()) {
			if (znak.equals("$")) {
				continue;
			}
			// sva stanja u koja moze doc sa ovim znakom
			List<Stanje> novaStanjaNakonPrijelaza = prijelazKapica(stanje, znak);

			/*
			 * Za svaki znak gledaj sve prijelaze koje ima
			 */
			for (Stanje novoStanje : novaStanjaNakonPrijelaza) {
				Stanje novoStanjeZaPrijelaze = new Stanje(
						novoStanje.getImeStanja());
				// ako to stanje vec postoji promjeni referencu na njega
				if (stvorenaStanja.contains(novoStanje)) {
					for (Stanje stvorenoStanje : stvorenaStanja) {
						if (stvorenoStanje.equals(novoStanje)) {
							novoStanjeZaPrijelaze = stvorenoStanje;
						}
					}
				} else {
					/*
					 * popuni novoStanjeZaPrijelaze sa svime sta ima novoStanje
					 * i sredi ga
					 */
					for (Produkcija produkcija : novoStanje
							.getlistuProdukcija()) {
						novoStanjeZaPrijelaze.dodajProdukcij(produkcija);
					}
					if (!novoStanje.getListaPrijelaza().isEmpty()) {
						for (Prijelaz buduciPrijelaz : novoStanje
								.getListaPrijelaza()) {
							novoStanjeZaPrijelaze.dodajPrijelaz(buduciPrijelaz);
						}
					}
					stvorenaStanja.add(novoStanjeZaPrijelaze);
					srediPrijelazeENkaNka(novoStanjeZaPrijelaze,
							stvorenaStanja, automat);
				}
				noviPrijelazi.add(new Prijelaz(znak, novoStanjeZaPrijelaze));

			}

		}

		stanje.setListaPrijelaza(noviPrijelazi);

	}

	private Automat nkaToDka(Automat nka) {
		Automat dka = new Automat();
		List<Stanje> stvorenaStanja = new ArrayList<Stanje>();
		Stanje dkaPocetno = new Stanje(nka.getPocetnoStanje());
		dka.setPocetnoStanje(dkaPocetno);

		// stvorenaStanja.add(dkaPocetno);
		srediPrijelazeNkaDka(dkaPocetno, stvorenaStanja);

		for (Stanje stanje : stvorenaStanja) {
			dka.dodajStanje(stanje);
		}
		return dka;
	}

	private void srediPrijelazeNkaDka(Stanje stanje, List<Stanje> stvorenaStanja) {

		List<Prijelaz> noviPrijelazi = new ArrayList<Prijelaz>();
		// zasto se tu pojavljuje $ kad stvaram iz eNka?
		// System.out.println(stanje.getZnakoviPrijelaza());
		for (String znak : stanje.getZnakoviPrijelaza()) {
			// preventivno
			if (znak.equals("$")) {
				continue;
			}

			TreeSet<Prijelaz> prijelazi = stanje.getListaPrijelazaPoZnaku(znak);

			String imeStanja = new String();

			for (Prijelaz prijelaz : prijelazi) {
				imeStanja += prijelaz.getNovoStanje().getImeStanja();
			}

			Stanje novoStanje = new Stanje(imeStanja);

			if (stvorenaStanja.contains(novoStanje)) {
				// vec postoji, promjeni referencu na njega
				for (Stanje stanjeIzListe : stvorenaStanja) {
					if (stanjeIzListe.equals(novoStanje)) {
						novoStanje = stanjeIzListe;
						break;
					}
				}
			} else { // stvori ga spajanje ova dva stanja
				for (Prijelaz prijelaz : prijelazi) {
					for (Produkcija produkcija : prijelaz.getNovoStanje()
							.getlistuProdukcija()) {
						novoStanje.dodajProdukcij(produkcija);
					}
					if (!prijelaz.getNovoStanje().getListaPrijelaza().isEmpty()) {
						for (Prijelaz buduciPrijelaz : prijelaz.getNovoStanje()
								.getListaPrijelaza()) {
							novoStanje.dodajPrijelaz(buduciPrijelaz);
						}
					}
				}
				stvorenaStanja.add(novoStanje);
				// sad sredi njega
				srediPrijelazeNkaDka(novoStanje, stvorenaStanja);
			}
			noviPrijelazi.add(new Prijelaz(znak, novoStanje));

		}
		stanje.setListaPrijelaza(noviPrijelazi);

	}

	private List<Stanje> prijelaz(List<Stanje> stanja, String znak) {
		List<Stanje> novaStanja = new ArrayList<Stanje>();

		for (Stanje stanje : stanja) {
			if (stanje.getListaPrijelaza().isEmpty()) {
				continue;
			}
			for (Prijelaz prijelaz : stanje.getListaPrijelazaPoZnaku(znak)) {
				if (!novaStanja.contains(prijelaz.getNovoStanje())) {
					novaStanja.add(prijelaz.getNovoStanje());
				}

			}
		}
		return novaStanja;

	}

	private List<Stanje> prijelazKapica(Stanje staroStanje, String znak) {

		List<Stanje> staraStanja = new ArrayList<Stanje>();
		staraStanja.add(staroStanje);

		List<Stanje> novaStanja = new ArrayList<Stanje>();

		novaStanja = getEpsilonOkruzenje(staraStanja);
		// System.out.println(novaStanja);
		List<Stanje> novaStanjaNakonPrijelaz = prijelaz(novaStanja, znak);
		// System.out.println(novaStanjaNakonPrijelaz);
		return getEpsilonOkruzenje(novaStanjaNakonPrijelaz);

	}

	private List<Stanje> getEpsilonOkruzenje(List<Stanje> stanja) {

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
		if (stanje.getListaPrijelaza().isEmpty()) {
			return;
		}
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
