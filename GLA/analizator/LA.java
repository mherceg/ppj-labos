import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LA {
	public static int pocetak=0,zadnjeNadeno=0,trenutni=0;
	Patuljak zadnjiNeNullPatuljak;

	public static void main(String[] args) {
		
		System.out.println("Run LA");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int red=1;
		String niz="";
		String source="";
		Patuljak zadnjiNeNull;
		String stanje;
		Snjeguljica snjeguljica;
		
		stanje=snjeguljica.getPocetno();
				
		
		// Compile Snjeguljice i onda konstruktor snjeguljice
		compile("Snjeguljica.java");
		
		
		try {
			while((niz = reader.readLine()) != null){
				source+=niz;
				source+="/n";
				
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
	
	private static void compile(String className){
		try{
		Process pro = Runtime.getRuntime().exec(className);
		printLines(className + " stdout:", pro.getInputStream());
	    printLines(className + " stderr:", pro.getErrorStream());
		}
		catch(Exception e){
			System.out.println("Greska kod compileanja");
			e.printStackTrace();
		}
	}
	
	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(name + " " + line);
	    }
	  }
}
