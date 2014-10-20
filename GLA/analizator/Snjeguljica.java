import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Snjeguljica {
	private List<Stanje> stanja;
	private String pocetno;
	
	Snjeguljica(String xmlPath) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Definicija.class);
		Unmarshaller um = context.createUnmarshaller();
		Definicija definicija = (Definicija) um.unmarshal(new File(xmlPath));
		
		pocetno = definicija.getPocetno();
		stanja = definicija.getStanje();

	}

	public Patuljak prelazim(String stanje, String ucitano) {

		for (Stanje element : stanja) {
			if (element.getIme().equals(stanje)) {
				for (Akcija akcija : element.getAkcija()) {
					String regex = akcija.getRegex();
					//System.out.println(regex + "HERC" +  ucitano);
					if (ucitano.matches(regex)) {
						String novoStanje = akcija.getNovoStanje();
						if(novoStanje==null){
							novoStanje=element.getIme();
						}
						return new Patuljak(novoStanje, akcija.getVraca(),
								akcija.getIdentifikator(), akcija.isNoviRed());
					}
				}
			}
		}
		return null;
	}

	public String getPocetno() {
		return pocetno;
	}
}
