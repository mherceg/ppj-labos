import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class GLA {
	
	static Map<String,String> mp;
	
	public static String srediGex(String a){		// TODO istestirat
		if(mp != null){
			Set<String> keys = mp.keySet();
			for(String key : keys){
				if(a.contains(key)){
					a = a.replace(key, "("+mp.get(key)+")");
				}
			}
		}
		// sve |$| pretvorit u "||" ali ako su sami	
		/*System.out.println("1 -> " + a);
		if(a.contains("|$|")){
			a = a.replaceAll("|$|", "||");
		}
		
		System.out.println(a);*/
		
		String[] chars = a.split("");
		
		// paran broj \ brisem
		// neparan broj \\ ostavljam
		Set<Integer> indexiZaBrisanje = new TreeSet<Integer>();
		for(int i=0; i<chars.length; i++){
			if(chars[i].equals("$")){
				int nOfBackSlasha = 0;
				int p = i;
				while(chars[--p].equals("\\")){
					nOfBackSlasha++;				
				}
				if(nOfBackSlasha%2 == 0){
					indexiZaBrisanje.add(i);
				}
			}
		}
		
		String[] cleanChars = new String[chars.length - indexiZaBrisanje.size()];
		int p=0;
		int i=0;
		while(i<cleanChars.length){
			if(! indexiZaBrisanje.contains(i)){
				cleanChars[i] = chars[p];
			}
			else{
				p++;
				continue;
			}
			i++;
			p++;
		}
		/*for(int i=0; i < cleanChars.length; p++){
			if(! indexiZaBrisanje.contains(i)){
				cleanChars[i] = chars[p];
				i+=1;
			}
		}*/
		
		a = String.join("", cleanChars);
		
		/*if(a.contains("$|")){
			a.replaceAll("$|", "");
		}
		if(a.contains("^")){
			a.replaceAll("^", "\\^");
		}
		if(a.contains("\\_")){
			a.replaceAll("\\_", "\\u0020");
		}		*/
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
			System.out.println(s[0].matches(s[1]));
			mp.put(s[0], s[1]);
		} while (! ucitano.startsWith("%"));
		while (ucitano.startsWith("%")){
			ucitano = reader.readLine();
		}
		
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
				s[0] = ucitano.substring(1, delim); //TODO Verify
				s[1] = ucitano.substring(delim +1);
				iIme = s[0];
				iGex = s[1];
			}
			if (red == 1){
				//ucitano mora bit {
				red = 2;
			}
			if (red == 2){
				if (ucitano.startsWith("}")){
					red = 0;
					Akcija a = new Akcija();
					a.setIdentifikator(iIden);
					a.setNoviRed(iNovi);
					a.setNovoStanje(iStanje);
					a.setRegex(iGex);
					a.setVraca(ivrati);
					for (Stanje s : def.getStanje()){
						if (s.getIme().equals(iIme)){
							s.getAkcija().add(a);
						}
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
	
	/*public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			while((line = reader.readLine()) != null){
				System.out.println(srediGex(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
