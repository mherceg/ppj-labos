import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class GSA {	

	public static void main(String[] args) {
		
		Definator definator = new Definator();
		String line;
		
		List<GramatickeProdukcije> listaGramtickihProdukcija = new ArrayList<GramatickaProdukcija>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			definator.dodajNezavrsnuListu(Arrays.asList(reader.readLine().replace("%V ","").split("\\s+")));
			definator.dodajZavrsnuListu(Arrays.asList(reader.readLine().replace("%T ","").split("\\s+")));
			definator.dodajSinkronizacijskuListu(Arrays.asList(reader.readLine().replace("%Syn ","").split("\\s+")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*System.out.println(definator.getNezavrsniZnakovi().toString());
		System.out.println(definator.getZavrsniZnakovi().toString());
		System.out.println(definator.getSinkronizacijskiZnakovi().toString());*/
		
		List<String> vecPunjeniNezavrsniZnakovi = new ArrayList<>();
		GramatickaProdukcija tempGramtickaProdukcija = new GramtickaProdukcija();
		
		try {
			while((line = reader.readLine()) != null){
				if(line.startsWith("<")){
					tempGramtickaProdukcija.setLijevaStrana(line);
					while((line = reader.readLine()).startsWith("\\s")){
						tempGramtickaProdukcija.
					}
				}
				
				
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
