import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GLA {

	static Map<String, String> mp;

	public static String srediGex(String a) {
		a = a.replace("^", "\\^").replace("+", "\\+").replace("?", "\\?")
				.replace("[", "\\[").replace("]", "\\]").replace(".", "\\.");
		if (mp != null) {
			Set<String> keys = mp.keySet();
			for (String key : keys) {
				if (a.contains(key)) {
					a = a.replace(key, "(" + mp.get(key) + ")");
				}
			}
		}

		// paran broj \ brisem
		// neparan broj \\ ostavljam
		Set<Integer> indexiZaBrisanje = new TreeSet<Integer>();
		Set<Integer> indexiZaPopunitRazmakom = new TreeSet<Integer>();

		byte[] stringBytes = null;
		try {
			stringBytes = a.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("String bytes are null");
			e.printStackTrace();
		}

		if (stringBytes != null) {
			// System.out.println("StringBytes nije null");
			indexiZaBrisanje.clear();
			indexiZaPopunitRazmakom.clear();
			for (int k = 0; k < stringBytes.length; k++) {

				if ((stringBytes[k] & 0xFF) == (0x5f)) { // underscore (0x5F)
					int nOfBackSlasha = 0;
					int tp = k;
					while (tp > 0 && (stringBytes[--tp] & 0xFF) == (0x5C)) {	// "\" (0x5C)
						nOfBackSlasha++;
					}
					if (nOfBackSlasha % 2 == 1) { // Mora bit neparan zbog
													// escape backslasha
//						System.out.println("Dodajem index za razmak -> "
//								+ (k - 1));
						indexiZaPopunitRazmakom.add(k - 1); // k-1 je \ koji
															// treba zamjenit sa
															// razmakom i
															// pobrisat _
					}
				}
				if((stringBytes[k] & 0xFF) == '$'){
					int nOfBackSlasha = 0;
					int tp = k;
					while (tp > 0 && (stringBytes[--tp] & 0xFF) == '\\') {	// 
						nOfBackSlasha++;
					}
					if (nOfBackSlasha % 2 == 0) { // Mora bit neparan
						indexiZaBrisanje.add(k); 	
					}
				}
			}
		}

		byte[] cleanCharsBytes = new byte[stringBytes.length
				- indexiZaBrisanje.size() - indexiZaPopunitRazmakom.size()];
		int p = 0;
		int i = 0;
		while (i < cleanCharsBytes.length) {
			if (indexiZaPopunitRazmakom.contains(p)) {
				p += 2;
				cleanCharsBytes[i] = 0x20; // Razmak (0x20)
				i++;

				continue;
			}
			if (indexiZaBrisanje.contains(p)) {
				p++;
				continue;
			}
			cleanCharsBytes[i] = stringBytes[p];
			i++;
			p++;
		}

		try {
			a = new String(cleanCharsBytes);
		} catch (Exception e) {
			System.err.println("bytes 2 string fail");
			e.printStackTrace();
		}

		return a;
	}

	public static void main(String[] args) throws IOException, JAXBException {

		Definicija def = new Definicija();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		mp = new HashMap<>();
		String ucitano;
		do {
			ucitano = reader.readLine();
			if (ucitano.startsWith("%X")) {
				break;
			}
			String[] s = ucitano.split(" ");
			mp.put(s[0], srediGex(s[1]));
		} while (!ucitano.startsWith("%"));
		def.setPocetno(ucitano.split(" ")[1].trim());
		ucitano = reader.readLine();

		String iIme = null;
		String iGex = null;
		String iIden = null;
		boolean iNovi = false;
		Integer ivrati = -1;
		String iStanje = null;

		int red = 0;
		while ((ucitano = reader.readLine()) != null) {
			if (red == 0) {
				red = 1;
				int delim = 0;
				String[] s = new String[2];
				for (int i = 0; ucitano.charAt(i) != '>'; ++i) {
					delim = i;
				}
				s[0] = ucitano.substring(1, delim + 1);
				s[1] = ucitano.substring(delim + 2);
				iIme = s[0];
				iGex = srediGex(s[1]);
			} else if (red == 1) {
				// ucitano mora bit {
				red = 2;
			} else if (red == 2) {
				if (ucitano.startsWith("}")) {
					red = 0;
					Akcija a = new Akcija();
					a.setIdentifikator(iIden);
					a.setNoviRed(iNovi);
					a.setNovoStanje(iStanje);
					a.setRegex(iGex);
					a.setVraca(ivrati);
					boolean flag = false;
					for (Stanje s : def.getStanje()) {
						if (s.getIme().equals(iIme)) {
							flag = true;
							s.getAkcija().add(a);
						}
					}
					if (!flag) {
						Stanje s = new Stanje();
						s.setIme(iIme);
						s.getAkcija().add(a);
						def.getStanje().add(s);
					}
					iIme = null;
					iGex = null;
					iIden = null;
					iNovi = false;
					ivrati = -1;
					iStanje = null;
				} else if (ucitano.startsWith("NOVI_REDAK")) {
					iNovi = true;
				} else if (ucitano.startsWith("UDJI_U_STANJE")) {
					iStanje = ucitano.split(" ")[1].trim();
				} else if (ucitano.startsWith("VRATI_SE")) {
					ivrati = Integer.parseInt(ucitano.split(" ")[1].trim());
				} else if (!ucitano.startsWith("-")) {
					iIden = ucitano.trim();
				}
			}
		}

		FileWriter fw = new FileWriter("analizator/Definicija.xml");
		JAXBContext context = JAXBContext.newInstance(Definicija.class);
		Marshaller um = context.createMarshaller();
		// StringWriter writer = new StringWriter();
		um.marshal(def, fw);
		// String xml = writer.toString();
		// System.out.println(xml);
		fw.close();
		System.out.println("Everything OK");
	}

	
	/*public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line;
		String cleanedLine;
		try {
			while ((line = reader.readLine()) != null) {
				cleanedLine = srediGex(line);
				System.out.println(cleanedLine);
				//break;
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
