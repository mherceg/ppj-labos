import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class GLA {
	
	static Map<String,String> mp;
	
	public static String srediGex(String a){
		return a;
	}

	public static void main(String[] args) throws IOException {
		
		Definicija def = new Definicija();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		mp = new HashMap<>();
		String ucitano;
		do{
			ucitano = reader.readLine();
			if (ucitano.startsWith("%X")){
				break;
			}
			String[] s = ucitano.split(" ");
			
			mp.put(s[0], s[1]);
		} while (! ucitano.startsWith("%"));
		while (ucitano.startsWith("%")){
			ucitano = reader.readLine();
		}
		int red = 0;
		Stanje st = new Stanje();
		while((ucitano = reader.readLine()) != null){
			if (red == 0){
				red = 1;
				int delim = 0;
				String[] s = new String[2];
				for (int i = 0; ucitano.charAt(i) != '>'; ++i){
					delim = i;
				}
				s[0] = ucitano.substring(1, delim); //TODO Verify
				s[1] = ucitano.substring(delim +1);
					
			}
		}
		
	}

}
