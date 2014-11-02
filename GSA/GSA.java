import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GSA {

	static Automat eNKA = new Automat();
	static Definator definator = new Definator();
	static String line;

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new ArrayList<GramatickaProdukcija>();

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
		for(Produkcija prod:listaProdukcija){
			prod.ispisi();
		}
		
	}

	private static void izGramatickihProdukcijaNapraviProdukcije() {

		for (GramatickaProdukcija produkcija : listaGramtickihProdukcija) {

			String lijevo = produkcija.getLijevaStrana();

			for (int i = 0; i <= produkcija.getDesnaStrana().size(); i++) {
				Produkcija novaProdukcija = new Produkcija(lijevo);
				if (i == 0) {
					novaProdukcija.setLjevoOdTockice(null);
					novaProdukcija.setDesnoOdTockice(produkcija
							.getDesnaStrana());
					listaProdukcija.add(novaProdukcija);

				} else if (i == produkcija.getDesnaStrana().size()) {
					novaProdukcija.setLjevoOdTockice(produkcija
							.getDesnaStrana());
					novaProdukcija.setDesnoOdTockice(null);
					listaProdukcija.add(novaProdukcija);

				} else {
					List<String> pomList = new LinkedList<String>();
					for (int j = 0; j < i; j++) {
						pomList.add(produkcija.getDesnaStrana().get(j));

					}
					novaProdukcija.setLjevoOdTockice(pomList);

					pomList.clear();

					for (int j = i; j < produkcija.getDesnaStrana().size(); j++) {
						pomList.add(produkcija.getDesnaStrana().get(j));

					}
					novaProdukcija.setDesnoOdTockice(pomList);

					listaProdukcija.add(novaProdukcija);

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
	
	private static void AutomatUTablice(Automat DKA) throws IOException, JAXBException{
		
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
