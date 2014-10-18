import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;


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

	public static void main(String[] args) throws IOException, JAXBException {
		
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
			def.setPocetno(ucitano.split(" ")[1].trim());
			ucitano = reader.readLine();
		
		String iIme = null;
		String iGex = null;
		String iIden = null;
		boolean iNovi = false;
		Integer ivrati = 0;
		String iStanje = null;
		
		int red = 0;
		while((ucitano = reader.readLine()) != null && ! ucitano.startsWith("XXX")){
			if (red == 0){
				red = 1;
				int delim = 0;
				String[] s = new String[2];
				for (int i = 0; ucitano.charAt(i) != '>'; ++i){
					delim = i;
				}
				s[0] = ucitano.substring(1, delim + 1 );
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
						s.setIme(iIme);
						s.getAkcija().add(a);
						def.getStanje().add(s);
					}
					iIme = null;
					iGex = null;
					iIden = null;
					iNovi = false;
					ivrati = 0;
					iStanje = null;
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
		
		FileWriter fw = new FileWriter("analizator/Definicija.xml");
		JAXBContext context = JAXBContext.newInstance(Definicija.class);
		Marshaller um = context.createMarshaller();
//		StringWriter writer =  new StringWriter();
		um.marshal(def, fw);
//		String xml = writer.toString();
//		System.out.println(xml);
		fw.close();
	}

}
