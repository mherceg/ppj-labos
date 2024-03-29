import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GSA {

	static Definator definator = new Definator();
	static String line;

	static final String znakZaKrajNiza = "Patuljak";
	static final String imePocetnogStanja = "Snjeguljica";

	static HashMap<String, List<String>> nezavrsniZnakoviZapocinjeMap = new HashMap<String, List<String>>();

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new LinkedList<GramatickaProdukcija>();

	static List<Produkcija> listaProdukcija = new LinkedList<Produkcija>();

	static List<String> listaPraznihZnakova = new LinkedList<String>();

	static BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));

	static BufferedWriter writer;

	public static void main(String[] args) throws IOException, JAXBException {

		try {
			definator.dodajNezavrsnuListu(Arrays.asList(reader.readLine()
					.replace("%V ", "").split("\\s+")));
			definator.dodajZavrsnuListu(Arrays.asList(reader.readLine()
					.replace("%T ", "").split("\\s+")));
			definator.dodajSinkronizacijskuListu(Arrays.asList(reader
					.readLine().replace("%Syn ", "").split("\\s+")));

			writer = new BufferedWriter(new FileWriter("Output.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println(definator.getNezavrsniZnakovi().toString());
		// System.out.println(definator.getZavrsniZnakovi().toString());
		// System.out.println(definator.getSinkronizacijskiZnakovi().toString());

		System.out.println();
		int brojDesneStraneProdukcije = 1;

		List<String> vecPunjeniNezavrsniZnakovi = new ArrayList<String>();
		GramatickaProdukcija tempGramtickaProdukcija = new GramatickaProdukcija();

		boolean dodaj = true;

		try {
			line = reader.readLine();
			while (line != null && !line.isEmpty()) {
				if (line.startsWith("<")) {
					tempGramtickaProdukcija = new GramatickaProdukcija();
					if (vecPunjeniNezavrsniZnakovi.contains(line)) {
						dodaj = false;
						tempGramtickaProdukcija = listaGramtickihProdukcija
								.get(getIndex(line, listaGramtickihProdukcija));
					} else {
						dodaj = true;
						tempGramtickaProdukcija.setLijevaStrana(line);
						vecPunjeniNezavrsniZnakovi.add(line);
					}
					line = reader.readLine();
				} else {
					while (Character.isWhitespace(line.charAt(0))) {
						tempGramtickaProdukcija.dodajNoviDesniIzraz(
								line.trim(), brojDesneStraneProdukcije++);
						line = reader.readLine();
						if (line == null || line.isEmpty()) {
							break;
						}
					}
					if (dodaj) {
						listaGramtickihProdukcija.add(tempGramtickaProdukcija);

					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		/*
		 * int i = 0; for (GramatickaProdukcija gramatickaProdukcija :
		 * listaGramtickihProdukcija) { i = 0;
		 * System.out.print(gramatickaProdukcija.getLijevaStrana());
		 * System.out.print(" -> "); List<String> desneStrane =
		 * gramatickaProdukcija.getDesnaStrana(); List<Integer> broj =
		 * gramatickaProdukcija.getRedosljedDesnihStrana(); for (String s :
		 * desneStrane) { System.out.print(s + "( " + broj.get(i++) + " ) |"); }
		 * System.out.println(); }
		 */

		izracunajPrazneZnakove();

		izracunajSkupoveZapocinjeZaNezavrsneZnakove();

		// System.out.println("#");
		// for (GramatickaProdukcija gramatickaProdukcija :
		// listaGramtickihProdukcija) {
		// System.out.print(gramatickaProdukcija.getLijevaStrana() + " -> ");
		// System.out.println(nezavrsniZnakoviZapocinjeMap
		// .get(gramatickaProdukcija.getLijevaStrana()));
		// }
		// System.out.println("#");
		// System.out.println();
		// System.out.println("!!");
		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			/*
			 * System.out.print(gramatickaProdukcija.getLijevaStrana() +
			 * " -> "); for(String s : gramatickaProdukcija.getDesnaStrana()){
			 * System.out.print(" " + s); } System.out.print( "{"); for(String s
			 * :
			 * izracunajSkupZapocinjeZaGramatickuProdukciju(gramatickaProdukcija
			 * )){ System.out.print(" " + s); } System.out.print(" }");
			 * System.out.println();
			 */
			izracunajSkupZapocinjeZaGramatickuProdukciju(gramatickaProdukcija);
		}
		// System.out.println("!!");
		// System.out.println();
		// System.out.println("%%");
		// System.out.println("prazni znakovi");
		// for (String prazanZnak : listaPraznihZnakova) {
		// System.out.println(prazanZnak);
		// }
		// System.out.println("%%");

		izGramatickihProdukcijaNapraviProdukcije();

		for (Produkcija produkcija : listaProdukcija) {
			String blank = "";
			if (!produkcija.getLjevoOdTockice().isEmpty()) {
				blank = "\t";
			} else {
				blank = "";
			}
			try {
				writer.write(blank + produkcija.toString());
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
			writer = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// ispisiProdukcije();
		List<Stanje> listaSvihStanja = new ArrayList<Stanje>();
		Automat eNka = napraviENKA(listaSvihStanja);

		// ispisiStanja(listaSvihStanja);

		System.out.println();
		System.out.println("Zavrsio eNka");

		AutomatonTranformator trasformator = new AutomatonTranformator();
		System.out.println("Zapoceo izradu dka");
		Automat dka = trasformator.eNkaToDka(eNka);
		System.out.println("Zavrsio dka");

		AutomatUTablice(dka);
		// ispisiProdukcije();
		// System.out.println("\n \nTu su sva Stanja");
		// ispisiStanja(listaSvihStanja);

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Automat napraviENKA(List<Stanje> listaSvihStanja) {
		Automat enka = new Automat();
		Stanje q0 = new Stanje(imePocetnogStanja);
		String pocetniNezavrsni = definator.getNezavrsniZnakovi().get(0);

		List<String> pocetnoDesno = new ArrayList<String>();
		pocetnoDesno.add(pocetniNezavrsni);

		Produkcija pocetnaProdukcija = new Produkcija("<%>", pocetnoDesno, 0);
		List<String> zapocinje = new ArrayList<>();
		zapocinje.add(znakZaKrajNiza);
		pocetnaProdukcija.setZapocinje(zapocinje);
		q0.dodajProdukcij(pocetnaProdukcija);

		Produkcija novaProdukcija = pocetnaProdukcija.createNextProdukcija();
		Stanje novoStanje = new Stanje(novaProdukcija.toString());
		novoStanje.dodajProdukcij(novaProdukcija);
		listaSvihStanja.add(novoStanje);
		q0.dodajPrijelaz(new Prijelaz(pocetniNezavrsni, novoStanje));

		for (Produkcija produkcija : listaProdukcija) {
			if (produkcija.getLeft().equals(pocetniNezavrsni)
					&& produkcija.getLjevoOdTockice().isEmpty()) {
				q0.dodajPrijelaz(new Prijelaz("$", stvoriStanje(
						pocetnaProdukcija, produkcija, listaSvihStanja)));
			}
		}

		enka.setPocetnoStanje(q0);

		for (Stanje stanje : listaSvihStanja) {
			enka.dodajStanje(stanje);
		}

		return enka;

	}

	private static Stanje stvoriStanje(Produkcija proslaProdukcija,
			Produkcija ovaPoslanaProdukcija, List<Stanje> listaSvihStanja) {

		if (ovaPoslanaProdukcija == null) {
			return null;
		}
		Produkcija ovaProdukcija = new Produkcija(ovaPoslanaProdukcija);
		// ovaProdukcija.getZapocinje().clear();
		// System.out.println(proslaProdukcija + "-----" + ovaProdukcija);

		// System.out.println(proslaProdukcija);

		List<String> tempList = new ArrayList<String>();

		if (proslaProdukcija == null) {
			tempList.add(znakZaKrajNiza);
		} else {
			tempList = izracunajSkupZapocinjeZaProdukciju(proslaProdukcija);
		}
		// System.out.println("TempList" + tempList);
		if (ovaProdukcija.getZapocinje().isEmpty()) {
			ovaProdukcija.setZapocinje(tempList);
		}
		Stanje novoStanje = new Stanje(ovaProdukcija.toString());

		novoStanje.dodajProdukcij(ovaProdukcija);

		if (listaSvihStanja.contains(novoStanje)) {
			return listaSvihStanja.get(listaSvihStanja.indexOf(novoStanje));
		} else {
			listaSvihStanja.add(novoStanje);
		}

		String sljedeciZnak;
		if (!ovaProdukcija.getDesnoOdTockice().isEmpty()) {
			sljedeciZnak = ovaProdukcija.getDesnoOdTockice().get(0);
		} else {
			return novoStanje;
		}

		// if (definator.getZavrsniZnakovi().contains(sljedeciZnak)) {
		Stanje sljedeceStanje = stvoriStanje(ovaProdukcija,
				ovaProdukcija.createNextProdukcija(), listaSvihStanja);

		if (sljedeceStanje != null) {

			if (!novoStanje.getListaPrijelaza().contains(
					new Prijelaz(sljedeciZnak, sljedeceStanje))) {

				novoStanje.dodajPrijelaz(new Prijelaz(sljedeciZnak,
						sljedeceStanje));
			}

		}

		// } else {
		for (Produkcija produkcija : listaProdukcija) {
			if (produkcija.getLeft().equals(sljedeciZnak)) {

				sljedeceStanje = stvoriStanje(ovaProdukcija, produkcija,
						listaSvihStanja);

				if (sljedeceStanje != null
						&& !novoStanje.getListaPrijelaza().isEmpty()) {

					if (!novoStanje.getListaPrijelaza().contains(
							new Prijelaz("$", sljedeceStanje))) {

						if (sljedeceStanje.getlistuProdukcija().get(0)
								.getLjevoOdTockice().isEmpty()) {

							novoStanje.dodajPrijelaz(new Prijelaz("$",
									sljedeceStanje));
						}
					}
				}
			}
		}
		// }

		return novoStanje;
	}

	/*
	 * private static void ispisiStanja(List<Stanje> listaSvihStanja) {
	 * 
	 * for (Stanje stanje : listaSvihStanja) {
	 * System.out.println(stanje.getImeStanja()); }
	 * 
	 * }
	 * 
	 * private static void ispisiProdukcije() { for (Produkcija prod :
	 * listaProdukcija) { prod.ispisi(); }
	 * 
	 * }
	 */
	private static void izGramatickihProdukcijaNapraviProdukcije() {

		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {

			List<String> tempList = new LinkedList<String>();
			Produkcija novaProdukcija;
			for (int i = 0; i < gramatickaProdukcija.getDesnaStrana().size(); i++) {
				// for (String pojedinacnaProdukcija : gramatickaProdukcija
				// .getDesnaStrana()) {
				/*
				 * Izbacuje prazne prijelaze
				 */
				String pojedinacnaProdukcija = gramatickaProdukcija
						.getDesnaStrana().get(i);
				int redniBroj = gramatickaProdukcija.getRedosljedDesnihStrana()
						.get(i);

				if (pojedinacnaProdukcija.equals("$")) {
					tempList = new LinkedList<String>();
					novaProdukcija = new Produkcija(
							gramatickaProdukcija.getLijevaStrana(), tempList,
							redniBroj);

				} else {
					/*
					 * Radi listu po kojoj napravi prvu produkciju
					 */
					tempList = new LinkedList<String>();
					String[] polje = pojedinacnaProdukcija.split(" ");
					for (String prod : polje) {
						tempList.add(prod);
					}
					novaProdukcija = new Produkcija(
							gramatickaProdukcija.getLijevaStrana(), tempList,
							redniBroj);

				}

				/*
				 * Dodamo produkciju u listu
				 */
				listaProdukcija.add(novaProdukcija);

				Produkcija iterator = novaProdukcija.createNextProdukcija();

				while (iterator != null) {

					listaProdukcija.add(iterator);

					iterator = iterator.createNextProdukcija();

				}

			}
		}

	}

	private static int getIndex(String string, List<GramatickaProdukcija> list) {
		int i = 0;
		System.out.println(list.size());
		for (GramatickaProdukcija gramatickaProdukcija : list) {
			if (gramatickaProdukcija.getLijevaStrana().equals(string)) {

				return i;
			} else {
				i++;
			}
		}
		return -1;
	}

	private static void izracunajPrazneZnakove() {
		List<String> prazniZnakovi = new LinkedList<String>();

		// Inicijalni prazni znakovi
		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			List<String> desneStrane = gramatickaProdukcija.getDesnaStrana();
			for (String desnaStrana : desneStrane) {
				if ((Arrays.asList(desnaStrana.split("\\s+"))).contains("$")) {
					prazniZnakovi.add(gramatickaProdukcija.getLijevaStrana());
				}
			}
		}
		// Dalje provjeravamo za sve dok se ne promijeni lista
		boolean podesi = true;
		while (podesi) {
			podesi = false;
			for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
				if (prazniZnakovi.contains(gramatickaProdukcija
						.getLijevaStrana())) {
					continue;
				}
				List<String> desneStrane = gramatickaProdukcija
						.getDesnaStrana();
				for (String desnaStrana : desneStrane) {
					if ((Arrays.asList(desnaStrana.split("\\s+")))
							.contains("$")) {
						prazniZnakovi.add(gramatickaProdukcija
								.getLijevaStrana());
					}
					if (prazniZnakovi.containsAll(Arrays.asList(desnaStrana
							.split("\\s+")))) {
						prazniZnakovi.add(gramatickaProdukcija
								.getLijevaStrana());
						podesi = true;
					}
				}
			}
		}
		listaPraznihZnakova = prazniZnakovi;
	}

	private static void izracunajSkupoveZapocinjeZaNezavrsneZnakove() {
		List<String> produkcijeSaEpsilonom = new ArrayList<String>();

		Map<String, Set<String>> pomocnaMapa = new HashMap<String, Set<String>>();

		// Potrpam u listu one sa epsilon prijalzima
		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			List<String> desneStrane = gramatickaProdukcija.getDesnaStrana();
			for (String desnaStrana : desneStrane) {
				if ((Arrays.asList(desnaStrana.split("\\s+"))).contains("$")) {
					produkcijeSaEpsilonom.add(gramatickaProdukcija
							.getLijevaStrana());
				}
			}
		}

		// Ubacim sve osim epsilone u kam ide.. cak i nezavrsne znakove
		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			Set<String> zapocinje = new HashSet<String>();
			List<String> desneStrane = gramatickaProdukcija.getDesnaStrana();
			for (String desnaStrana : desneStrane) {
				String[] znakoviDesneStrane = desnaStrana.split("\\s+");
				for (int i = 0; i < znakoviDesneStrane.length; i++) {
					if (definator.getZavrsniZnakovi().contains(
							znakoviDesneStrane[i])) {
						zapocinje.add(znakoviDesneStrane[i]);
						break;
					}
					if (i == 0
							&& definator.getNezavrsniZnakovi().contains(
									znakoviDesneStrane[i])) {
						zapocinje.add(znakoviDesneStrane[i]);
					}
					if (i > 0
							&& definator.getNezavrsniZnakovi().contains(
									znakoviDesneStrane[i])
							&& produkcijeSaEpsilonom
									.contains(znakoviDesneStrane[i - 1])) {
						zapocinje.add(znakoviDesneStrane[i]);
					}
					if (!produkcijeSaEpsilonom.contains(znakoviDesneStrane[i])) {
						break;
					}

				}
				if (znakoviDesneStrane.length != 0
						&& !znakoviDesneStrane[0].equals("$")) {
					zapocinje.add(znakoviDesneStrane[0]);
				}
			}
			pomocnaMapa.put(gramatickaProdukcija.getLijevaStrana(), zapocinje);
		}

		boolean podesi = true; // mijenja sve nezavrsne sa njegovom desnom
								// stranom dok ne nestanu svi nezavrsni
		while (podesi) {
			podesi = false;
			for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
				Set<String> zapocinje = pomocnaMapa.get(gramatickaProdukcija
						.getLijevaStrana());
				zapocinje.remove(gramatickaProdukcija.getLijevaStrana()); // micem
																			// <A>
																			// ->
																			// <A>
																			// da
																			// nebude
																			// beskonacno
				pomocnaMapa.put(gramatickaProdukcija.getLijevaStrana(),
						zapocinje);
			}
			for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
				Set<String> zapocinje = pomocnaMapa.get(gramatickaProdukcija
						.getLijevaStrana());
				for (GramatickaProdukcija gramatickaProdukcija2 : listaGramtickihProdukcija) {
					if (zapocinje.contains(gramatickaProdukcija2
							.getLijevaStrana())) {
						podesi = true;
						zapocinje.remove(gramatickaProdukcija2
								.getLijevaStrana());
						zapocinje.addAll(pomocnaMapa.get(gramatickaProdukcija2
								.getLijevaStrana()));
					}
				}
				pomocnaMapa.put(gramatickaProdukcija.getLijevaStrana(),
						zapocinje);
			}

		}

		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			List<String> lista = new ArrayList<String>();
			lista.addAll(pomocnaMapa.get(gramatickaProdukcija.getLijevaStrana()));
			nezavrsniZnakoviZapocinjeMap.put(
					gramatickaProdukcija.getLijevaStrana(), lista);
		}

		// Pocetnom znaku stavljamo znak za kraj niza
		List<String> lista = nezavrsniZnakoviZapocinjeMap.get(definator
				.getNezavrsniZnakovi().get(0));
		lista.add(znakZaKrajNiza);
		nezavrsniZnakoviZapocinjeMap.put(
				definator.getNezavrsniZnakovi().get(0), lista);

	}

	private static List<String> izracunajSkupZapocinjeZaProdukciju(
			Produkcija produkcija1) {
		/*
		 * NAPRAVIO SAM TI DA IMAS SVOJU PRODUKCIJU DA NASU NE MJENJAS
		 */
		Produkcija produkcija = new Produkcija(produkcija1);

		List<String> skupZapocinje = new LinkedList<String>();
		List<String> desnoOdTockice = produkcija.getDesnoOdTockice();
		boolean skipFirst = true;
		// A -> alpha * B betha
		for (String znakDesneStrane : desnoOdTockice) { // 1. uvjet -> znak b
														// element
														// zapocinje(betha)
			if (skipFirst) {
				skipFirst = false; // preskacemo prvi znak
				continue;
			}
			if (definator.getNezavrsniZnakovi().contains(znakDesneStrane)) {
				skupZapocinje.addAll(nezavrsniZnakoviZapocinjeMap
						.get(znakDesneStrane));
			} else if (definator.getZavrsniZnakovi().contains(znakDesneStrane)) {

				skupZapocinje.add(znakDesneStrane);

				break;
			}
		}
		// 2. uvjet -> ako je iz niza betha moguce generirati prazan niz ili je
		// vec prazan niz -> zapocinje od A
		if (isMoguceGeneriratPrazanNiz(desnoOdTockice)) {
			/*
			 * TU SI JA MISLIM FAILO JER NISI PROVJERAVAO DUPLICE
			 */
			for (String nekaj : produkcija.getZapocinje()) {
				if (!skupZapocinje.contains(nekaj)) {
					skupZapocinje.add(nekaj);
				}
			}
		}

		return skupZapocinje;
	}

	private static boolean isMoguceGeneriratPrazanNiz(List<String> niz) {

		niz.remove(0);
		if (niz.size() == 0) {
			return true;
		}
		for (String znak : niz) {
			if (listaPraznihZnakova.contains(znak)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	private static List<String> izracunajSkupZapocinjeZaGramatickuProdukciju(
			GramatickaProdukcija produkcija) {
		List<String> desneStrane = produkcija.getDesnaStrana();
		Set<String> skupZapocinje = new HashSet<String>();

		for (String desnaStrana : desneStrane) {
			skupZapocinje.clear();
			String[] znakoviDesneStrane = desnaStrana.split("\\s+");
			for (String znak : znakoviDesneStrane) {
				if (definator.getNezavrsniZnakovi().contains(znak)) {
					skupZapocinje
							.addAll(nezavrsniZnakoviZapocinjeMap.get(znak));
				} else if (definator.getZavrsniZnakovi().contains(znak)) {
					skupZapocinje.add(znak);
					break;
				}
			}
			/*
			 * System.out.print("Zapocinje(" + desnaStrana + ")" + " -> {"); for
			 * (String s : skupZapocinje) { System.out.print(" " + s); }
			 * System.out.println(" }");
			 */
		}

		return null;
	}

	private static HashMap<String, Integer> brojceki;
	private static int max;

	private static int brojcek(String imeStanja) {
		if (brojceki == null) {
			brojceki = new HashMap<>();
			max = -1;
		}
		if (brojceki.containsKey(imeStanja)) {
			return brojceki.get(imeStanja);
		} else {
			brojceki.put(imeStanja, ++max);
			return max;
		}
	}

	private static void AutomatUTablice(Automat DKA) throws IOException,
			JAXBException {

		Tablica akcija = new Tablica();
		Tablica novoStanje = new Tablica();

		akcija.setPocetno("" + brojcek(DKA.getPocetnoStanje().getImeStanja()));
		DKA.dodajStanje(DKA.getPocetnoStanje());

		for (Stanje s : DKA.getStanja()) {
			for (Prijelaz p : s.getListaPrijelaza()) {
				if (p.getZnak().startsWith("<")) {
					novoStanje.setAkcija("" + brojcek(s.getImeStanja()), p
							.getZnak(), new Akcija(Tip.Stavi, ""
							+ brojcek(p.getNovoStanje().getImeStanja()), null,
							0));
				} else {
					akcija.setAkcija("" + brojcek(s.getImeStanja()), p
							.getZnak(), new Akcija(Tip.Pomakni, ""
							+ brojcek(p.getNovoStanje().getImeStanja()), null,
							0));
				}
			}
			for (Produkcija p : s.getlistuProdukcija()) {
				if (p.getDesnoOdTockice() == null
						|| p.getDesnoOdTockice().isEmpty()) {
					for (String z : p.getZapocinje()) {
						Akcija ak = akcija.getAkcija(
								"" + brojcek(s.getImeStanja()), z);
						if (ak == null || ak.getAkcija() == Tip.Reduciraj
								&& ak.getBroj() < p.getRedniBrojPojavljivanja())
							akcija.setAkcija(
									"" + brojcek(s.getImeStanja()),
									z,
									new Akcija(Tip.Reduciraj, p.getLeft(), p
											.getLjevoOdTockice(), p
											.getRedniBrojPojavljivanja()));
					}
				}
			}
			if (s.getlistuProdukcija().get(0).getLeft()
							.startsWith("<%>") && (s.getlistuProdukcija().get(0).getDesnoOdTockice() == null || s.getlistuProdukcija().get(0).getDesnoOdTockice().isEmpty())) {
				akcija.setAkcija("" + brojcek(s.getImeStanja()),
						znakZaKrajNiza, new Akcija(Tip.Prihvati));
			}
		}

		FileWriter fw = new FileWriter("analizator/Akcija.xml");
		JAXBContext context = JAXBContext.newInstance(Tablica.class);
		Marshaller um = context.createMarshaller();
		um.marshal(akcija, fw);
		fw.close();

		fw = new FileWriter("analizator/NovoStanje.xml");
		context = JAXBContext.newInstance(Tablica.class);
		um = context.createMarshaller();
		um.marshal(novoStanje, fw);
		fw.close();
		
		Sink s = new Sink();
		s.setLista(definator.getSinkronizacijskiZnakovi());
		
		fw = new FileWriter("analizator/Sink.xml");
		context = JAXBContext.newInstance(Sink.class);
		um = context.createMarshaller();
		um.marshal(s, fw);
		fw.close();
	}

}
