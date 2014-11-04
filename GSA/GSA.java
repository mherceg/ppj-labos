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

public class GSA {

	static Automat eNKA = new Automat();
	static Definator definator = new Definator();
	static String line;

	static HashMap<String, List<String>> zapocinjeMap = new HashMap<String, List<String>>();

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new LinkedList<GramatickaProdukcija>();

	static List<Produkcija> listaProdukcija = new LinkedList<Produkcija>();

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

		izracunajSkupoveZapocinje();

		System.out.println("#");
		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			System.out.print(gramatickaProdukcija.getLijevaStrana() + " -> ");
			System.out.println(zapocinjeMap.get(gramatickaProdukcija
					.getLijevaStrana()));
		}
		System.out.println("#");

		izGramatickihProdukcijaNapraviProdukcije();
		System.out.println();
		System.out.println();

		ispisiProdukcije();

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void ispisiProdukcije() {
		for (Produkcija prod : listaProdukcija) {
			prod.ispisi();
		}

	}

	private static void izGramatickihProdukcijaNapraviProdukcije() {

		for (GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija) {
			for (String pojedinacnaProdukcija : gramatickaProdukcija
					.getDesnaStrana()) {
				List<String> tempList = new ArrayList<String>();
				String[] polje= pojedinacnaProdukcija.split(" ");
				for(String prod: polje){
					tempList.add(prod);
				}
				Produkcija novaProdukcija = new Produkcija(tempList);

				novaProdukcija.ispisi();

				listaProdukcija.add(novaProdukcija);

				Produkcija iterator = novaProdukcija.createNextProdukcija();

				iterator.ispisi();

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

	private static void izracunajSkupoveZapocinje() {
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
			zapocinjeMap.put(gramatickaProdukcija.getLijevaStrana(), lista);
		}

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
