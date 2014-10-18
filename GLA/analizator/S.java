import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class S implements Snjeguljica {
	private List<Stanje> stanja;

	S(String xmlPath) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Definicija.class);
		Unmarshaller um = context.createUnmarshaller();
		Definicija definicija = (Definicija) um.unmarshal(new File(xmlPath));

		stanja = definicija.getStanje();

	}

	public Patuljak prelazim(String stanje, String ucitano) {

		for (Stanje element : stanja) {
			if (element.getIme().equals(stanje)) {
				for (Akcija akcija : element.getAkcija()) {
					String regex = akcija.getRegex();
					if (ucitano.matches(regex)) {
						String novoStanje = akcija.getNovoStanje();
						if(novoStanje.equals(null)){
							novoStanje=element.getIme();
						}
						return new P(akcija.getNovoStanje(), akcija.getVraca(),
								akcija.getIdentifikator(), akcija.isNoviRed());
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getPocetno() {
		return null;
	}
}
