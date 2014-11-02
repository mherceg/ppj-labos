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
	
	// obavezno dodati
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
