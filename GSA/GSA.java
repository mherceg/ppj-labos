import java.io.BufferedReader;
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

public class GSA {

	static Automat eNKA = new Automat();
	static Definator definator = new Definator();
	static String line;
	
	static HashMap<String, List<String>> zapocinjeMap = new HashMap<String, List<String>>();

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new LinkedList<GramatickaProdukcija>();

	static BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));
	static List<Produkcija> listaProdukcija = new ArrayList<Produkcija>();

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
		for(GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija){
			System.out.print(gramatickaProdukcija.getLijevaStrana() + " -> ");
			System.out.println(zapocinjeMap.get(gramatickaProdukcija.getLijevaStrana()));
		}
		System.out.println("#");
		
		izGramatickihProdukcijaNapraviProdukcije();

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void izGramatickihProdukcijaNapraviProdukcije() {

		for (GramatickaProdukcija produkcija : listaGramtickihProdukcija) {

			for (String desnaStrana : produkcija.getDesnaStrana()) {
				String lijevo = produkcija.getLijevaStrana();
				String desno = desnaStrana;
				napraviSvePrijelazeStockicom(lijevo, desno);
			}

		}

	}

	private static void napraviSvePrijelazeStockicom(String lijevo, String desno) {
		String[] desnoKaoPolje = desno.split("");
		System.out.println(desnoKaoPolje);

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
	
	private static void izracunajSkupoveZapocinje(){
		List<String> produkcijeSaEpsilonom = new ArrayList<String>();
		
		Map<String, Set<String>> pomocnaMapa = new HashMap<String, Set<String>>();
		
		// Ubacim sve osim epsilone u kam ide.. cak i nezavrsne znakove
		for(GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija){
			Set<String> zapocinje = new HashSet<String>();
			List<String> desneStrane = gramatickaProdukcija.getDesnaStrana();
			for(String desnaStrana : desneStrane){
				String[] znakoviDesneStrane = desnaStrana.split("\\s+");
				if(znakoviDesneStrane[0].equals("$")){
					produkcijeSaEpsilonom.add(gramatickaProdukcija.getLijevaStrana());
				}
				else{
					zapocinje.add(znakoviDesneStrane[0]);
				}
			}
			pomocnaMapa.put(gramatickaProdukcija.getLijevaStrana(), zapocinje);
		}
		
		// Pobrinem se za epsilone -> ako neki nezavrsni ide u epsilom, u desnim stranama tih produkcija
		// pretvorim taj zavesni u sve kaj on ide.
		for(GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija){
			Set<String> zapocinje = pomocnaMapa.get(gramatickaProdukcija.getLijevaStrana());
			for(GramatickaProdukcija gramatickaProdukcija2 : listaGramtickihProdukcija){
				if(zapocinje.contains(gramatickaProdukcija2.getLijevaStrana()) &&
						produkcijeSaEpsilonom.contains(gramatickaProdukcija2.getLijevaStrana())){
					zapocinje.remove(gramatickaProdukcija2.getLijevaStrana());
					zapocinje.addAll(pomocnaMapa.get(gramatickaProdukcija2.getLijevaStrana()));
				}
			}
		}
		
		// Samo maknem sve preostale nezavrsne u kam ide
		for(GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija){
			Set<String> zapocinje = pomocnaMapa.get(gramatickaProdukcija.getLijevaStrana());
			for(GramatickaProdukcija gramatickaProdukcija2 : listaGramtickihProdukcija){
				if(zapocinje.contains(gramatickaProdukcija2.getLijevaStrana())){
					zapocinje.remove(gramatickaProdukcija2.getLijevaStrana());				
				}
			}
		}
		
		for(GramatickaProdukcija gramatickaProdukcija : listaGramtickihProdukcija){
			List<String> lista = new ArrayList<String>();
			lista.addAll(pomocnaMapa.get(gramatickaProdukcija.getLijevaStrana()));
			zapocinjeMap.put(gramatickaProdukcija.getLijevaStrana(), lista);
		}				
		
	}

}
