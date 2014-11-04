import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AutomatonTranformator {

	public static void main(String[] args) {
		Stanje prvo = new Stanje("0");
		Stanje drugo = new Stanje("1");
		Stanje trece = new Stanje("2");
		prvo.dodajPrijelaz(new Prijelaz("0", prvo));
		prvo.dodajPrijelaz(new Prijelaz("$", drugo));
		drugo.dodajPrijelaz(new Prijelaz("1", drugo));
		drugo.dodajPrijelaz(new Prijelaz("$", trece));
		trece.dodajPrijelaz(new Prijelaz("2", trece));

		prvo.dodajProdukcij(new Produkcija("nekaj"));
		drugo.dodajProdukcij(new Produkcija("nekaj"));
		trece.dodajProdukcij(new Produkcija("nekaj"));

		Automat novi = new Automat();
		novi.dodajStanje(prvo);
		novi.dodajStanje(drugo);
		novi.dodajStanje(trece);
		novi.setPocetnoStanje(prvo);

		// Stanje prvo2= new Stanje("0");
		// Stanje drugo2= new Stanje("1");
		//
		// prvo2.dodajPrijelaz(new Prijelaz("0", prvo2));
		// prvo2.dodajPrijelaz(new Prijelaz("0", drugo2));
		// prvo2.dodajPrijelaz(new Prijelaz("1", drugo2));
		//
		// drugo2.dodajPrijelaz(new Prijelaz("1", prvo2));
		// drugo2.dodajPrijelaz(new Prijelaz("1", drugo2));
		//
		// prvo2.dodajProdukcij(new Produkcija("nekaj"));
		// drugo2.dodajProdukcij(new Produkcija("nekaj"));
		//
		// Automat novi2= new Automat();
		// novi2.dodajStanje(prvo2);
		// novi2.dodajStanje(drugo2);
		// novi2.setPocetnoStanje(prvo2);
		//

		AutomatonTranformator auto = new AutomatonTranformator();
		Automat nka = auto.eNkaToNka(novi);
		System.out.println(1);
		Automat dka = auto.nkaToDka(nka);
		System.out.println(2);

	}

	public Automat eNkaToDka(Automat eNka) {
		return nkaToDka(eNkaToNka(eNka));
	}

	private Automat eNkaToNka(Automat eNka) {

		Automat nka = new Automat();
		List<Stanje> stvorenaStanja = new ArrayList<Stanje>();
		Stanje nkaPocetno = new Stanje(eNka.getPocetnoStanje());
		nka.setPocetnoStanje(nkaPocetno);

		stvorenaStanja.add(nkaPocetno);
		srediPrijelazeENkaNka(nkaPocetno, stvorenaStanja);

		for (Stanje stanje : stvorenaStanja) {
			nka.dodajStanje(stanje);
		}

		return nka;
	}

	private void srediPrijelazeENkaNka(Stanje stanje,
			List<Stanje> stvorenaStanja) {
		List<Prijelaz> noviPrijelazi = new ArrayList<Prijelaz>();
		/*
		 * Prodi po svakom znaku za to stanje
		 */
		for (String znak : stanje.getZnakoviPrijelaza()) {
			if (znak.equals("$")) {
				continue;
			}
			// samo zbog prijelazKapica
			List<Stanje> staroStanjeUListi = new ArrayList<Stanje>();
			staroStanjeUListi.add(stanje);
			// sva stanja u koja moze doc sa ovim znakom
			List<Stanje> novaStanjaNakonPrijelaza = prijelazKapica(
					staroStanjeUListi, znak);
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
					for (Prijelaz buduciPrijelaz : novoStanje
							.getListaPrijelaza()) {
						novoStanjeZaPrijelaze.dodajPrijelaz(buduciPrijelaz);
					}
					stvorenaStanja.add(novoStanjeZaPrijelaze);
					srediPrijelazeENkaNka(novoStanjeZaPrijelaze, stvorenaStanja);
				}
				noviPrijelazi.add(new Prijelaz(znak, novoStanjeZaPrijelaze));
			}

		}
		stanje.setZnakoviPrijelaza(noviPrijelazi);

	}

	private Automat nkaToDka(Automat nka) {
		Automat dka = new Automat();
		List<Stanje> stvorenaStanja = new ArrayList<Stanje>();
		Stanje dkaPocetno = new Stanje(nka.getPocetnoStanje());
		dka.setPocetnoStanje(dkaPocetno);

		stvorenaStanja.add(dkaPocetno);
		srediPrijelazeNkaDka(dkaPocetno, stvorenaStanja);

		for (Stanje stanje : stvorenaStanja) {
			dka.dodajStanje(stanje);
		}

		return dka;
	}

	private void srediPrijelazeNkaDka(Stanje stanje, List<Stanje> stvorenaStanja) {

		List<Prijelaz> noviPrijelazi = new ArrayList<Prijelaz>();
		// zasto se tu pojavljuje $ kad stvaram iz eNka?
		System.out.println(stanje.getZnakoviPrijelaza());
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
					for (Prijelaz buduciPrijelaz : prijelaz.getNovoStanje()
							.getListaPrijelaza()) {
						novoStanje.dodajPrijelaz(buduciPrijelaz);
					}
				}
				stvorenaStanja.add(novoStanje);
				// sad sredi njega
				srediPrijelazeNkaDka(novoStanje, stvorenaStanja);
			}
			noviPrijelazi.add(new Prijelaz(znak, novoStanje));

		}
		stanje.setZnakoviPrijelaza(noviPrijelazi);

	}

	private List<Stanje> prijelaz(List<Stanje> stanja, String znak) {
		List<Stanje> novaStanja = new ArrayList<Stanje>();

		for (Stanje stanje : stanja) {
			for (Prijelaz prijelaz : stanje.getListaPrijelazaPoZnaku(znak)) {
				if (!novaStanja.contains(prijelaz.getNovoStanje())) {
					novaStanja.add(prijelaz.getNovoStanje());
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
