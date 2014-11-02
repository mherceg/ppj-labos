import java.util.ArrayList;
import java.util.List;

public class Stanje {
	private String imeStanja;
	private List<Prijelaz> listaPrijelaza = new ArrayList<Prijelaz>();
	private List<Produkcija> listaProdukcija = new ArrayList<Produkcija>();

	public Stanje(String imeStanja) {
		super();
		this.imeStanja = imeStanja;
	}


	
	public void dodajProdukcij(Produkcija nova){
		listaProdukcija.add(nova);
	}


	public void dodajPrijelaz(Prijelaz novi) {
		if (!listaPrijelaza.contains(novi)) {
			listaPrijelaza.add(novi);
		}
	}

	public String getImeStanja() {
		return imeStanja;
	}

	public void setImeStanja(String imeStanja) {
		this.imeStanja = imeStanja;
	}

	public List<Prijelaz> getListaPrijelaza() {
		if (listaPrijelaza.isEmpty()) {
			return null;
		} else {
			return listaPrijelaza;
		}

	}
	public void setZnakoviPrijelaza(List<Prijelaz> novo){
		listaPrijelaza.clear();
		for(Prijelaz prijelaz : novo){
			listaPrijelaza.add(new Prijelaz(prijelaz.getZnak(), prijelaz.getNovoStanje()));
		}
	}

	public List<String> getZnakoviPrijelaza() {
		List<String> nadeniZnakovi = new ArrayList<String>();
		for (Prijelaz prijelaz : this.listaPrijelaza) {
			if (!nadeniZnakovi.contains(prijelaz.getZnak())) {
				nadeniZnakovi.add(prijelaz.getZnak());
			}
		}
		return nadeniZnakovi;
	}

	public List<Prijelaz> getListaPrijelazaPoZnaku(String znak) {
		List<Prijelaz> nadeniPrijelazi = new ArrayList<Prijelaz>();
		for (Prijelaz prijelaz : this.listaPrijelaza) {
			if (prijelaz.getZnak().equals(znak)) {
				nadeniPrijelazi.add(prijelaz);
			}
		}

		return nadeniPrijelazi;

	}

	public List<Produkcija> getlistuProdukcija() {
		if (listaProdukcija.isEmpty()) {
			return null;
		} else {
			return listaProdukcija;
		}
	}
	
	// obavezno dodati, ista su ako imaju ista imena
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
