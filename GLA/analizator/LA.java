import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.bind.JAXBException;

public class LA {
	public static int pocetak = 0, zadnjeNadeno = 0, trenutni = 0;
	public static Patuljak zadnjiNeNullPatuljak;
	public static int red = 1;
	public static String stanje;

	public static void main(String[] args) {

		/*
		 * Inicijalizacija pocetnih varijabli
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		String niz = "";
		String source = "";
		Patuljak patuljak = null;

		Snjeguljica snjeguljica = null;
		try {
			snjeguljica = new Snjeguljica("Definicija.xml");
		} catch (JAXBException e2) {
			e2.printStackTrace();
		}

		// Compile Snjeguljice i onda konstruktor snjeguljice
		//compile("Snjeguljica.java");

		/*
		 * Pocetno stanje dobivamo iz snjeguljice i trebalo bi bit pocetno
		 */
		stanje = snjeguljica.getPocetno();

		/*
		 * Ucitavanje s stdina i radenje ogromnog stringa u kojem je sve
		 */
		try {
			while ((niz = reader.readLine()) != null) {
				source += niz;
				source += '\n';
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Slanje snjeguljici znakove jedan po jedan da ona matcha i nase
		 * trenutno stanje koje onda kasnije kroz patuljka gledam
		 */
		while (pocetak < source.length()) {
			// Postavljamo neNullPatuljka na null te idemo ispocetka svaki put
			zadnjiNeNullPatuljak = null;
			// Idemo for petlju po svakom substringu u nizu
			for (int i = pocetak; i < source.length(); i++) {
				//System.out.println(stanje);
				patuljak = snjeguljica.prelazim(stanje,
						source.substring(pocetak, i + 1));
				obradiPatuljka(patuljak, i + 1);
			}
			// Ako nije nista matchao do kraja ulaza onda je greska i ispisuje
			// se znak
			if (zadnjiNeNullPatuljak == null) {
				ispisiGresku((source.substring(pocetak, pocetak+1)), red);
				pocetak++;
				trenutni = pocetak;
				zadnjeNadeno=pocetak;
			}
			// Ako je nasao neki povoljni match onda ga ispise i idemo dalje osim ako nema identifikator
			else {
				if (zadnjiNeNullPatuljak.getNasao()!=null){
					if (zadnjiNeNullPatuljak.getVraca() != -1){
						zadnjeNadeno = pocetak + zadnjiNeNullPatuljak.getVraca();
					}
					ispisiIzlaz(source.substring(pocetak, zadnjeNadeno), red);
				}
				int uzmiPrvihToliko = zadnjiNeNullPatuljak.getVraca();
				
				if(uzmiPrvihToliko!=-1){
					pocetak = pocetak + uzmiPrvihToliko;
				}else{
					pocetak = zadnjeNadeno;
				}
				stanje = zadnjiNeNullPatuljak.getStanje();
				trenutni = pocetak;
				zadnjeNadeno=pocetak;
			}
		}
	}

	/**
	 * Uzima parametar patuljka i znak do kojeg je dosao te gleda da li je ista
	 * matchano, ako je onda pamti string do kojeg je matcha, a ako nije matchan
	 * se vraca i ide dalje. Gleda da li je patuljak poslao da se doslo u novi
	 * red, ako je povecava globalni brojac redaka i pamti
	 * 
	 * @param patuljak
	 *            koji nam je dosao sa informacijama
	 * @param i
	 *            je substring do kojeg je dosao od pocetka
	 */
	private static void obradiPatuljka(Patuljak patuljak, int i) {
		// Ako je patuljak null onda se samo vraca i nije matchao ni jednu
		// leksicku jedinku
		if (patuljak == null) {
			return;
		} else { // ako je nasao nesto onda pamti tog patuljka i pamti substring
			zadnjiNeNullPatuljak = patuljak;
			//System.out.println(zadnjiNeNullPatuljak);
			zadnjeNadeno = i;
			// povecava brojac reda ako je potrebno
			if (patuljak.getNoviRed() == true) {
				red++;

			}

		}

	}

	/**
	 * ispisivanje leksikih jedinki
	 * 
	 * @param izvorniTekst
	 *            tekst koji je kljucna rijec za odredenu jedinku
	 * @param red
	 */
	public static void ispisiIzlaz(String izvorniTekst, int red) {
		// ispisujem string od pocetka do zadnjeg nadenog
		String tekstZaIspis = izvorniTekst;//.substring(pocetak, zadnjeNadeno);
		//Patuljak Mirko = zadnjiNeNullPatuljak;
		String uniformniZnak = zadnjiNeNullPatuljak.getNasao();

		System.out.println(uniformniZnak + " " + red + " " + tekstZaIspis);

		
		//System.out.println("stanje promijenjeno" + stanje);

	}

	/**
	 * Ispisivanje greske
	 * 
	 * @param greska
	 *            je greska u kodu
	 * @param red
	 *            je linija reda
	 */
	public static void ispisiGresku(String greska, int red) {

		System.err.println("ERROR " + red + " " + greska);

	}

	/*private static void compile(String className) {
		try {
			String command = "javac "+className;
			Process pro = Runtime.getRuntime().exec(command);
			printLines(className + " stdout:", pro.getInputStream());
			printLines(className + " stderr:", pro.getErrorStream());
		} catch (Exception e) {
			System.out.println("Greska kod compileanja");
			e.printStackTrace();
		}
	}

	private static void printLines(String name, InputStream ins)
			throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(name + " " + line);
		}
	}*/
}
