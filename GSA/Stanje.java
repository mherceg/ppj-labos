import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class Stanje {
	private String imeStanja;
	private List<Prijelaz> listaPrijelaza;
	private List<Produkcija> listaProdukcija;

	public Stanje(String imeStanja) {
		super();
		this.imeStanja = imeStanja;
		listaPrijelaza = new ArrayList<Prijelaz>();
		listaProdukcija = new ArrayList<Produkcija>();
	}

	public Stanje(Stanje drugo) {
		listaPrijelaza = new LinkedList<Prijelaz>();
		listaProdukcija = new LinkedList<Produkcija>();

		this.imeStanja = new String(drugo.getImeStanja());
		for (Prijelaz prijelaz : drugo.getListaPrijelaza()) {
			this.listaPrijelaza.add(new Prijelaz(prijelaz));
		}
		if (drugo.getlistuProdukcija() != null) {
			for (Produkcija produkcija : drugo.getlistuProdukcija()) {
				this.listaProdukcija.add(new Produkcija(produkcija));
			}
		}
	}

	public void dodajProdukcij(Produkcija nova) {
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

	public void setZnakoviPrijelaza(List<Prijelaz> novo) {
		listaPrijelaza.clear();
		for (Prijelaz prijelaz : novo) {
			listaPrijelaza.add(new Prijelaz(prijelaz.getZnak(), prijelaz
					.getNovoStanje()));
		}
	}

	public TreeSet<String> getZnakoviPrijelaza() {
		TreeSet<String> nadeniZnakovi = new TreeSet<String>();
		for (Prijelaz prijelaz : this.listaPrijelaza) {
			if (!nadeniZnakovi.contains(prijelaz.getZnak())) {
				nadeniZnakovi.add(prijelaz.getZnak());
			}
		}
		return nadeniZnakovi;
	}

	public TreeSet<Prijelaz> getListaPrijelazaPoZnaku(String znak) {
		TreeSet<Prijelaz> nadeniPrijelazi = new TreeSet<Prijelaz>();
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Stanje)) {
			return false;
		}
		return this.imeStanja.equals(((Stanje) obj).getImeStanja());
	}

	@Override
	public int hashCode() {
		return this.getImeStanja().hashCode();
	}

	@Override
	public String toString() {
		String sve;
		sve = "Stanje :" + this.imeStanja;
		for (Prijelaz prijelaz : this.listaPrijelaza) {
			sve = sve + "\n" + prijelaz.toString();
		}
		for (Produkcija produkcija : this.listaProdukcija) {
			sve = sve + "\n" + produkcija.toString();
		}

		return sve;
	}
}
