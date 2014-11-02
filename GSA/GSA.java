import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GSA {	

	public static void main(String[] args) {
		
		Definator definator = new Definator();
		String line;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Stanje stanjeUuctavanju;
		
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
		
		try {
			while((line = reader.readLine()) != null){
				
				
				
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
