import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;

import javax.xml.bind.JAXBException;

public class LA {
	public static int pocetak = 0, zadnjeNadeno = 0, trenutni = 0;
	public static Patuljak zadnjiNeNullPatuljak;
	public static int red = 1;

	public static void main(String[] args) {

		System.out.println("Run LA");
		/*
		 * Inicijalizacija pocetnih varijabli
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		String niz = "";
		String source = "";
		Patuljak patuljak;
		String stanje;
		Snjeguljica snjeguljica = null;
		try {
			snjeguljica = new S("Definicija.xml");
		} catch (JAXBException e2) {
			// TODO Auto-generated catch block
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
				source += "/n";
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
				patuljak = snjeguljica.prelazim(
						source.substring(pocetak, i + 1), stanje);
				obradiPatuljka(patuljak, i + 1);
			}
			// Ako nije nista matchao do kraja ulaza onda je greska i ispisuje
			// se znak
			if (zadnjiNeNullPatuljak == null) {
				ispisiGresku((source.substring(pocetak, 1)), red);
			}
			// Ako je nasao neki povoljni match onda ga ispise i idemo dalje
			else {
				ispisiIzlaz(source.substring(pocetak, zadnjeNadeno), red);
				pocetak = pocetak + zadnjeNadeno;
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
			zadnjeNadeno = i;
			//povecava brojac reda ako je potrebno
			if (patuljak.getNoviRed() == true) {
				red++;

			}

		}

	}

	/**
	 * ispisivanje leksi�kih jedinki
	 * 
	 * @param izvorniTekst
	 *            tekst koji je kljucna rijec za odredenu jedinku
	 * @param red
	 */
	public static void ispisiIzlaz(String izvorniTekst, int red) {
		// ispisujem string od pocetka do zadnjeg nadenog
		String tekstZaIspis = izvorniTekst.substring(pocetak, zadnjeNadeno);

		String uniformniZnak = zadnjiNeNullPatuljak.getNasao();

		System.out.println(uniformniZnak + " " + red + " " + tekstZaIspis);

	}

	/**
	 * Ispisivanje gre�ke
	 * 
	 * @param greska
	 *            je gre�ka u kodu
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
