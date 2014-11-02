import java.util.ArrayList;
import java.util.List;

public class AutomatonTranformator {
	
	public static void main(String[] args) {
		Stanje prvo= new Stanje("0");
		Stanje drugo= new Stanje("1");
		Stanje trece = new Stanje("2");
		prvo.dodajPrijelaz(new Prijelaz("0", prvo));
		prvo.dodajPrijelaz(new Prijelaz("$", drugo));
		drugo.dodajPrijelaz(new Prijelaz("1", drugo));
		drugo.dodajPrijelaz(new Prijelaz("$", trece));
		trece.dodajPrijelaz(new Prijelaz("2", trece));
		
		prvo.dodajProdukcij(new Produkcija("nekaj"));
		drugo.dodajProdukcij(new Produkcija("nekaj"));
		trece.dodajProdukcij(new Produkcija("nekaj"));
		
		Automat novi= new Automat();
		novi.dodajStanje(prvo);
		novi.dodajStanje(drugo);
		novi.dodajStanje(trece);
		novi.setPocetnoStanje(prvo);
		
		AutomatonTranformator auto= new AutomatonTranformator();
		auto.eNkaToNka(novi);
		System.out.println(1);
		
	}

	public Automat eNkaToNka(Automat eNka) {
		Automat nka = new Automat();
		
		

		for (Stanje staroStanje : eNka.getStanja()) {

			Stanje novoStanje = new Stanje(staroStanje.getImeStanja());
			List<String> znakoviPrijelaza = staroStanje.getZnakoviPrijelaza();

			for (String znak : znakoviPrijelaza) {

				List<Stanje> staroStanjeUListi = new ArrayList<Stanje>();
				staroStanjeUListi.add(staroStanje);

				List<Stanje> novaStanjaNakonPrijelaza = prijelazKapica(
						staroStanjeUListi, znak);

				for (Stanje stanjeNakonPrijelaza : novaStanjaNakonPrijelaza) {
					novoStanje.dodajPrijelaz(new Prijelaz(znak,
							stanjeNakonPrijelaza));
				}

				novoStanje.dodajProdukcij(staroStanje.getlistuProdukcija().get(
						0));
			}
			nka.dodajStanje(novoStanje);
		}
		return nka;
	}

	public Automat nkaToDka(Automat nka) {
		Automat dka = new Automat();

		return dka;
	}

	private void srediPrijelazeNkaDka(Stanje stanje, List<Stanje> stvorenaStanja) {
		for (String znak : stanje.getZnakoviPrijelaza()) {
			List<Prijelaz> prijelazi = stanje.getListaPrijelazaPoZnaku(znak);

			String imeStanja = new String();

			for (Prijelaz prijelaz : prijelazi) {
				imeStanja += prijelaz.getNovoStanje().getImeStanja();

			}
			
			Stanje novoStanje = new Stanje(imeStanja);
			
			if (stvorenaStanja.contains(novoStanje)) {
				//vec postoji, promjeni referencu na njega
				for (Stanje stanjeIzListe : stvorenaStanja) {
					if (stanjeIzListe.equals(novoStanje)) {
						novoStanje = stanjeIzListe;
						break;
					}
				}
			} else { //stvori ga spajanje ova dva stanja
				for (Prijelaz prijelaz : prijelazi) {
					novoStanje.dodajProdukcij(prijelaz.getNovoStanje().getlistuProdukcija().get(0));
					for(Prijelaz buduciPrijelaz:prijelaz.getNovoStanje().getListaPrijelaza()){
						//pazi da ne dodajes duplo
						novoStanje.dodajPrijelaz(buduciPrijelaz);
					}

				}
				stvorenaStanja.add(novoStanje);
				//sad sredi njega
				srediPrijelazeNkaDka(novoStanje, stvorenaStanja);
			}
			
			
		}

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
