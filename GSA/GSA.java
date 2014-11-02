import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GSA {

	static Automat eNKA = new Automat();
	static Definator definator = new Definator();
	static String line;

	static List<GramatickaProdukcija> listaGramtickihProdukcija = new ArrayList<GramatickaProdukcija>();

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

		izGramatickihProdukcijaNapraviProdukcije();

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void izGramatickihProdukcijaNapraviProdukcije() {

		for (GramatickaProdukcija produkcija : listaGramtickihProdukcija) {

			String lijevo=produkcija.getLijevaStrana();
			
			for(int i=0;i<produkcija.getDesnaStrana().size();i++){
				Produkcija novaProdukcija=new Produkcija(lijevo);
				if(i==0){
					novaProdukcija.setLjevoOdTockice(null);
					novaProdukcija.setDesnoOdTockice(produkcija.getDesnaStrana());
					
				}
				else if(i==produkcija.getDesnaStrana().size()-1){
					novaProdukcija.setLjevoOdTockice(produkcija.getDesnaStrana());
					novaProdukcija.setDesnoOdTockice(null);
					
				}
				else{
					
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

}
