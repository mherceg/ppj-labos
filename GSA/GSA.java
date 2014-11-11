import java.io.BufferedReader;
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

import org.ietf.jgss.Oid;

public class GSA {

	static Definator definator = new Definator();
	static String line;
	
	static final String znakZaKrajNiza = "┴";

	static HashMap<String, List<String>> nezavrsniZnakoviZapocinjeMap = new HashMap<String, List<String>>();

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new LinkedList<GramatickaProdukcija>();

	static List<Produkcija> listaProdukcija = new LinkedList<Produkcija>();

	static List<Stanje> listaSvihStanja = new LinkedList<Stanje>();
	
	static List<String> listaPraznihZnakova = new LinkedList<String>();

	static BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));

	public static void main(String[] args) {

		try {
			definator.dodajNezavrsnuListu(Arrays.asList(reader.readLine()
					.replace("%V ", "").split("\\s+")));
			definator.dodajZavrsnuListu(Arrays.asList(reader.readLine()
					.replace("%T ", "").split("\\s+")));
			definator.dodajSinkronizacijskuListu(Arrays.asList(reader
					.readLine().replace("%Syn ", "").split("\\s+")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(definator.getNezavrsniZnakovi().toString());
		System.out.println(definator.getZavrsniZnakovi().toString());
		System.out.println(definator.getSinkronizacijskiZnakovi().toString());

		System.out.println();

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
						tempGramtickaProdukcija
								.dodajNoviDesniIzraz(line.trim());
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

		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			System.out.print(gramatickaProdukcija.getLijevaStrana());
			System.out.print(" -> ");
			List<String> desneStrane = gramatickaProdukcija.getDesnaStrana();
			for (String s : desneStrane) {
				System.out.print(s + "|");
			}
			System.out.println();
		}

		izracunajPrazneZnakove();
		
		izracunajSkupoveZapocinjeZaNezavrsneZnakove();

		System.out.println("#");
		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			System.out.print(gramatickaProdukcija.getLijevaStrana() + " -> ");
			System.out.println(nezavrsniZnakoviZapocinjeMap.get(gramatickaProdukcija
					.getLijevaStrana()));
		}
		System.out.println("#");
		System.out.println();
		System.out.println("!!");
		for(GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija){
			/*System.out.print(gramatickaProdukcija.getLijevaStrana() + " -> ");
			for(String s : gramatickaProdukcija.getDesnaStrana()){
				System.out.print(" " + s);
			}
			System.out.print( "{");
			for(String s : izracunajSkupZapocinjeZaGramatickuProdukciju(gramatickaProdukcija)){
				System.out.print(" " + s);
			}
			System.out.print(" }");
			System.out.println();*/
			izracunajSkupZapocinjeZaGramatickuProdukciju(gramatickaProdukcija);
		}
		System.out.println("!!");
		System.out.println();
		System.out.println("%%");
		System.out.println("prazni znakovi");
		for(String prazanZnak : listaPraznihZnakova){
			System.out.println(prazanZnak);
		}
		System.out.println("%%");

		izGramatickihProdukcijaNapraviProdukcijeIStanja();

		dodajEpsilonProdukcije();

		Automat eNka = izgradiAutomat();

		System.out.println();
		System.out.println("Zavrsio eNka");
		
		AutomatonTranformator trasformator = new AutomatonTranformator();
		Automat dka = trasformator.eNkaToDka(eNka);
		System.out.println("Zavrsio dka");

//		ispisiProdukcije();
//		System.out.println("\n \nTu su sva Stanja");
//		ispisiStanja();

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

	private static Automat izgradiAutomat() {
		Stanje pocetno = new Stanje("q0");

		String pocetniZnakGramatike = listaGramtickihProdukcija.get(0)
				.getLijevaStrana();

		for (Stanje stanje : listaSvihStanja) {
			System.out.println(stanje);
			if (stanje.getlistuProdukcija().get(0).getLeft()
					.equals(pocetniZnakGramatike)) {
				pocetno.dodajPrijelaz(new Prijelaz("$", stanje));
			}
		}

		Automat eNka = new Automat();
		
//		eNKA.dodajStanje(pocetno);
		eNka.setPocetnoStanje(pocetno);

		for (Stanje stanje : listaSvihStanja) {
			eNka.dodajStanje(stanje);
		}
		
		return eNka;

	}

	private static void dodajEpsilonProdukcije() {
		String trazi;
		for (Stanje stanjeKojeObradujem : listaSvihStanja) {
			if (stanjeKojeObradujem.getlistuProdukcija() != null
					&& !(stanjeKojeObradujem.getlistuProdukcija().get(0)
							.getDesnoOdTockice().isEmpty())) {
				trazi = stanjeKojeObradujem.getlistuProdukcija().get(0)
						.getDesnoOdTockice().get(0);
				for (Stanje stanjeKojimaJeLijevaStranaTrazi : listaSvihStanja) {
					if (stanjeKojimaJeLijevaStranaTrazi.getlistuProdukcija()
							.get(0).getLeft().equals(trazi)
							&& stanjeKojimaJeLijevaStranaTrazi
									.getlistuProdukcija().get(0)
									.getLjevoOdTockice().isEmpty()) {
						stanjeKojeObradujem.dodajPrijelaz(new Prijelaz("$",
								stanjeKojimaJeLijevaStranaTrazi));
					}
				}
			}
		}

	}

	private static void ispisiStanja() {

		for (Stanje stanje : listaSvihStanja) {
			System.out.println(stanje.toString());
		}

	}

	private static void ispisiProdukcije() {
		for (Produkcija prod : listaProdukcija) {
			prod.ispisi();
		}

	}

	private static void izGramatickihProdukcijaNapraviProdukcijeIStanja() {

		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			String imeStanja;
			List<String> tempList = new LinkedList<String>();
			Produkcija novaProdukcija;
			for (String pojedinacnaProdukcija : gramatickaProdukcija
					.getDesnaStrana()) {
				/*
				 * Izbacuje prazne prijelaze
				 */

				if (pojedinacnaProdukcija.equals("$")) {
					System.err.println("DOSO $");
					tempList = new LinkedList<String>();
					novaProdukcija = new Produkcija(
							gramatickaProdukcija.getLijevaStrana(), tempList,
							nezavrsniZnakoviZapocinjeMap.get(gramatickaProdukcija
									.getLijevaStrana()));

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
							nezavrsniZnakoviZapocinjeMap.get(gramatickaProdukcija
									.getLijevaStrana()));

				}

				/*
				 * Slaze ime stanja
				 */
				imeStanja = novaProdukcija.getLeft() + "->";
				if (pojedinacnaProdukcija.equals("$")) {
					imeStanja += "*";

					System.err.println(imeStanja);
				} else {
					if (novaProdukcija.getLjevoOdTockice() != null) {
						imeStanja += novaProdukcija.getLjevoOdTockice();
					}
					imeStanja += "*";
					if (novaProdukcija.getDesnoOdTockice() != null) {
						imeStanja += novaProdukcija.getDesnoOdTockice();
					}
				}

				/*
				 * Stvaranje produkcije
				 */
				Stanje prethodnoStanje = new Stanje(imeStanja);
				prethodnoStanje.dodajProdukcij(novaProdukcija);

				/*
				 * Dodamo produkciju u listu
				 */
				listaProdukcija.add(novaProdukcija);

				/*
				 * Trazimo dalje produkcije
				 */
				if (pojedinacnaProdukcija.equals("$")) {
					listaSvihStanja.add(prethodnoStanje);
					continue;
				}

				Produkcija iterator = novaProdukcija.createNextProdukcija();

				Stanje trenutnoStanje = null;
				String prviLijevoOdTockice;
				if (iterator != null) {

					imeStanja = iterator.getLeft() + "->";
					if (iterator.getLjevoOdTockice() != null) {
						imeStanja += iterator.getLjevoOdTockice();
					}
					imeStanja += "*";
					if (iterator.getDesnoOdTockice() != null) {
						imeStanja += iterator.getDesnoOdTockice();
					}

					trenutnoStanje = new Stanje(imeStanja);
					trenutnoStanje.dodajProdukcij(iterator);
					prviLijevoOdTockice = iterator.getLjevoOdTockice().get(
							iterator.getLjevoOdTockice().size() - 1);
					prethodnoStanje.dodajPrijelaz(new Prijelaz(
							prviLijevoOdTockice, trenutnoStanje));
					listaSvihStanja.add(prethodnoStanje);
					listaProdukcija.add(iterator);
					prethodnoStanje = trenutnoStanje;
					iterator = iterator.createNextProdukcija();

				} else {
					continue;
				}

				while (iterator != null) {
					imeStanja = iterator.getLeft() + "->";
					if (iterator.getLjevoOdTockice() != null) {
						imeStanja += iterator.getLjevoOdTockice();
					}
					imeStanja += "*";
					if (iterator.getDesnoOdTockice() != null) {
						imeStanja += iterator.getDesnoOdTockice();
					}

					trenutnoStanje = new Stanje(imeStanja);
					trenutnoStanje.dodajProdukcij(iterator);
					prviLijevoOdTockice = iterator.getLjevoOdTockice().get(
							iterator.getLjevoOdTockice().size() - 1);
					prethodnoStanje.dodajPrijelaz(new Prijelaz(
							prviLijevoOdTockice, trenutnoStanje));
					listaSvihStanja.add(prethodnoStanje);
					listaProdukcija.add(iterator);

					prethodnoStanje = trenutnoStanje;
					iterator = iterator.createNextProdukcija();

				}

				listaSvihStanja.add(prethodnoStanje);

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
					prazniZnakovi.add(gramatickaProdukcija
							.getLijevaStrana());
				}
			}
		}
		//Dalje provjeravamo za sve dok se ne promijeni lista
		boolean podesi = true;
		while(podesi){
			podesi = false;
			for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
				if(prazniZnakovi.contains(gramatickaProdukcija.getLijevaStrana())){
					continue;
				}
				List<String> desneStrane = gramatickaProdukcija.getDesnaStrana();
				for (String desnaStrana : desneStrane) {
					if ((Arrays.asList(desnaStrana.split("\\s+"))).contains("$")) {
						prazniZnakovi.add(gramatickaProdukcija
								.getLijevaStrana());
					}
					if(prazniZnakovi.containsAll(Arrays.asList(desnaStrana.split("\\s+")))){
						prazniZnakovi.add(gramatickaProdukcija.getLijevaStrana());
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
			nezavrsniZnakoviZapocinjeMap.put(gramatickaProdukcija.getLijevaStrana(), lista);
		}
		
		//Pocetnom znaku stavljamo znak za kraj niza
		List<String> lista = nezavrsniZnakoviZapocinjeMap.get(definator.getNezavrsniZnakovi().get(0));
		lista.add(znakZaKrajNiza);
		nezavrsniZnakoviZapocinjeMap.put(definator.getNezavrsniZnakovi().get(0), lista);

	}
	
	private static List<String> izracunajSkupZapocinjeZaProdukciju(Produkcija produkcija){
		List<String> skupZapocinje = new LinkedList<String>();
		List<String> desnoOdTockice = produkcija.getDesnoOdTockice();
		boolean skipFirst = true;
									// A -> alpha * B betha
		for(String znakDesneStrane : desnoOdTockice){		// 1. uvjet -> znak b element zapocinje(betha)
			if(skipFirst){
				skipFirst = false;	// preskacemo prvi znak
				continue;
			}
			if(definator.getNezavrsniZnakovi().contains(znakDesneStrane)){
				skupZapocinje.addAll(nezavrsniZnakoviZapocinjeMap.get(znakDesneStrane));
			}
			else if(definator.getZavrsniZnakovi().contains(znakDesneStrane)){
				skupZapocinje.add(znakDesneStrane);
				break;
			}			
		}
		// 2. uvjet -> ako je iz niza betha moguce generirati prazan niz ili je vec prazan niz -> zapocinje od A
		if(isMoguceGeneriratPrazanNiz(desnoOdTockice)){
			skupZapocinje.addAll(produkcija.getZapocinje());
		}
		
		return skupZapocinje;
	}
	
	private static boolean isMoguceGeneriratPrazanNiz(List<String> niz){
		niz.remove(0);
		if(niz.size() == 0){
			return true;
		}
		for(String znak : niz){
			if(listaPraznihZnakova.contains(znak)){
				continue;
			}
			else{
				return false;
			}
		}
		return true;
	}
	
	private static List<String> izracunajSkupZapocinjeZaGramatickuProdukciju(GramatickaProdukcija produkcija){
		List<String> desneStrane = produkcija.getDesnaStrana();
		Set<String> skupZapocinje = new HashSet<String>();
		
		for(String desnaStrana : desneStrane){
			skupZapocinje.clear();
			String[] znakoviDesneStrane = desnaStrana.split("\\s+");
			for(String znak : znakoviDesneStrane){
				if(definator.getNezavrsniZnakovi().contains(znak)){
					skupZapocinje.addAll(nezavrsniZnakoviZapocinjeMap.get(znak));
				}
				else if(definator.getZavrsniZnakovi().contains(znak)){
					skupZapocinje.add(znak);
					break;
				}
			}
			System.out.print("Zapocinje(" + desnaStrana + ")" + " -> {");
			for(String s : skupZapocinje){
				System.out.print(" " + s);
			}
			System.out.println(" }");
		}

		return null;
	}

	private static void AutomatUTablice(Automat DKA) throws IOException,
			JAXBException {

		Tablica akcija = new Tablica();
		Tablica novoStanje = new Tablica();

		akcija.setPocetno(DKA.getPocetnoStanje().getImeStanja());

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
	}

}
