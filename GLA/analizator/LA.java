import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LA {
	public static int pocetak,zadnjeNadeno,trenutni;
	Patuljak zadnjiNeNullPatuljak;

	public static void main(String[] args) {
		
		String niz;
		Patuljak zadnjiNeNull;
		
		// Compile Snjeguljice i onda konstruktor snjeguljice
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int red=1;
		
		try {
			while((niz = reader.readLine()) != null){
				
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		
		try{
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * ispisivanje leksièkih jedinki
	 * @param izvorniTekst tekst koji je kljucna rijec za odredenu jedinku
	 * @param red
	 */
	public void ispisiIzlaz(String izvorniTekst, int red){
		//ispisujem string od pocetka do zadnjeg nadenog
		String tekstZaIspis=izvorniTekst.substring(pocetak,zadnjeNadeno);
		
		String uniformniZnak=zadnjiNeNullPatuljak.getNasao();
		
		System.out.println(uniformniZnak+" "+red+" "+tekstZaIspis);
		
		
		
	}
	/**
	 * Ispisivanje greške
	 * @param greska je greška u kodu
	 * @param red je linija reda
	 */
	public void ispisiGresku(String greska, int red){
		
		System.err.println("ERROR " + red + " "+ greska);
		
	}
	
}
