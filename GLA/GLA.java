import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GLA {
	
	static Map<String,String> mp;
	
	public static String srediGex(String a){		// TODO istestirat
		Set<String> keys = mp.keySet();
		for(String key : keys){
			if(a.contains(key)){
				a.replace(key, "("+mp.get(key)+")");
			}
		}
		if(a.contains("$")){
			a.replace("$", "");
		}
		if(a.contains("^")){
			a.replace("^", "\\^");
		}
		if(a.contains("\\_")){
			a.replace("\\_", "\\u0020");
		}		
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
			//System.out.println(s[0].matches(s[1]));
			mp.put(s[0], srediGex(s[1]));
		} while (! ucitano.startsWith("%"));
			ucitano = reader.readLine();
		
		String iIme = null;
		String iGex = null;
		String iIden = null;
		boolean iNovi = false;
		Integer ivrati = 0;
		String iStanje = null;
		
		int red = 0;
		while((ucitano = reader.readLine()) != null){
			if (red == 0){
				red = 1;
				int delim = 0;
				String[] s = new String[2];
				for (int i = 0; ucitano.charAt(i) != '>'; ++i){
					delim = i;
				}
				s[0] = ucitano.substring(1, delim + 1 ); //TODO Verify
				s[1] = ucitano.substring(delim + 2);
				iIme = s[0];
				iGex = srediGex(s[1]);
			}
			else
			if (red == 1){
				//ucitano mora bit {
				red = 2;
			}
			else
			if (red == 2){
				if (ucitano.startsWith("}")){
					red = 0;
					Akcija a = new Akcija();
					a.setIdentifikator(iIden);
					a.setNoviRed(iNovi);
					a.setNovoStanje(iStanje);
					a.setRegex(iGex);
					a.setVraca(ivrati);
					boolean flag = false;
					for (Stanje s : def.getStanje()){
						if (s.getIme().equals(iIme)){
							flag = true;
							s.getAkcija().add(a);
						}
					}
					if (!flag){
						Stanje s = new Stanje();
						s.getAkcija().add(a);
						def.getStanje().add(s);
					}
				}
				else
				if (ucitano.startsWith("NOVI_REDAK")){
					iNovi = true;
				}
				else
				if (ucitano.startsWith("UDJI_U_STANJE")){
					iStanje = ucitano.split(" ")[1].trim();
				}
				else 
				if (ucitano.startsWith("VRATI_SE")){
					ivrati = Integer.parseInt(ucitano.split(" ")[1].trim());
				}
				else 
				if ( !ucitano.startsWith("-")){
					iIden = ucitano.trim();
				}
			}
		}
		
		//TODO Ispisi XML
		
	}

}
