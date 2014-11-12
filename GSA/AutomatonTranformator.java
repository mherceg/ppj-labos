import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AutomatonTranformator {

	public static void main(String[] args) {
		Stanje nula = new Stanje("0");
		Stanje jedan = new Stanje("1");
		Stanje dva = new Stanje("2");

		/*nula.dodajProdukcij(new Produkcija("<A0>", new ArrayList<String>(),
				new ArrayList<String>()));
		jedan.dodajProdukcij(new Produkcija("<A1>", new ArrayList<String>(),
				new ArrayList<String>()));
		dva.dodajProdukcij(new Produkcija("<A2>", new ArrayList<String>(),
				new ArrayList<String>()));*/

		nula.dodajPrijelaz(new Prijelaz("$", jedan));
		nula.dodajPrijelaz(new Prijelaz("0", nula));
		nula.dodajPrijelaz(new Prijelaz("1", dva));

		jedan.dodajPrijelaz(new Prijelaz("$", dva));
		jedan.dodajPrijelaz(new Prijelaz("1", jedan));

		dva.dodajPrijelaz(new Prijelaz("2", dva));

		Automat eNka = new Automat();
		eNka.dodajStanje(nula);
		eNka.dodajStanje(jedan);
		eNka.dodajStanje(dva);

		Stanje pocetno = new Stanje("pocetno");
		pocetno.dodajPrijelaz(new Prijelaz("$", nula));

		eNka.setPocetnoStanje(pocetno);
		// eNka.dodajStanje(pocetno);

		AutomatonTranformator auto = new AutomatonTranformator();

		Automat nka = auto.eNkaToNka(eNka);
		System.out.println("zavrsio nka");
		Automat dka = auto.nkaToDka(nka);
		// System.out.println("zavrsio dka");

	}

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

		return nka;
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

			// System.out.format("%s e za %s: %s%n", stanje.getImeStanja(),
			// znak,
			// novaStanjaNakonPrijelaza);

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
		stanje.setZnakoviPrijelaza(noviPrijelazi);

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
		stanje.setZnakoviPrijelaza(noviPrijelazi);

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
