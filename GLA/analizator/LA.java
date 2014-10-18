import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LA {
	public static int pocetak,zadnjeNadeno,trenutni;
	Patuljak zadnjiNeNullPatuljak;

	public static void main(String[] args) {
		
		System.out.println("Run LA");
		
		String niz;
		Patuljak zadnjiNeNull;
		
		// Compile Snjeguljice i onda konstruktor snjeguljice
		compile("Snjeguljica.java");
		
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
	
	public void ispisiIzlaz(String izvorniTekst, int red){
		//ispisujem string od pocetka do zadnjeg nadenog
		String tekstZaIspis=izvorniTekst.substring(pocetak,zadnjeNadeno);
		
		String uniformniZnak;
		
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
