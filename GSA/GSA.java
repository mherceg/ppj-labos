import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GSA {

	static Automat eNKA = new Automat();
	static Definator definator = new Definator();
	static String line;

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new ArrayList<GramatickaProdukcija>();

	static BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));
	static List<Produkcija> listaProdukcija=new ArrayList<Produkcija>();

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

		try {
			line = reader.readLine();
			while (line != null) {
				if (line.startsWith("<")) {
					if (vecPunjeniNezavrsniZnakovi.contains(line)) {
						tempGramtickaProdukcija = listaGramtickihProdukcija
								.get(getIndex(line, listaGramtickihProdukcija));
					} else {
						tempGramtickaProdukcija.setLijevaStrana(line);
						vecPunjeniNezavrsniZnakovi.add(line);
					}
				} else {
					while ((line = reader.readLine()).startsWith("\\s")) {
						tempGramtickaProdukcija
								.dodajNoviDesniIzraz(line.trim());
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

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void izGramatickihProdukcijaNapraviProdukcije() {
		

		for (GramatickaProdukcija produkcija : listaGramtickihProdukcija) {
			
			for(String desnaStrana: produkcija.getDesnaStrana()){
				String lijevo=produkcija.getLijevaStrana();
				String desno=desnaStrana;
				napraviSvePrijelazeStockicom(lijevo,desno);
			}
			
		}

	}



	private static void napraviSvePrijelazeStockicom(String lijevo, String desno) {
		String[] desnoKaoPolje=desno.split("");
		System.out.println(desnoKaoPolje);
		
	}

	private static int getIndex(String string, List<GramatickaProdukcija> list) {
		int i = 0;
		for (GramatickaProdukcija gramatickaProdukcija : list) {
			if (gramatickaProdukcija.getLijevaStrana().equals(string)) {
				return i;
			} else {
				i++;
			}
		}
		return -1;
	}

}
