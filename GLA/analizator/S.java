import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class S implements Snjeguljica {
	private Definicija definicija;
	
	
	S(String xmlPath) throws JAXBException{
		
		JAXBContext context = JAXBContext.newInstance(Definicija.class);
	    Unmarshaller um = context.createUnmarshaller();
	    definicija = (Definicija) um.unmarshal( new File(xmlPath) );
	
		
//		JAXBContext context = JAXBContext.newInstance(Definicija.class);
//		Unmarshaller um = context.createUnmarshaller();
//		definicija = (Definicija) um.unmarshal(new StringReader(xmlPath));
		
	}
	
	
	
	public Patuljak prelazim(String stanje,String ucitano){
		
		return null;
	}

	@Override
	public String getPocetno() {
		return null;
	}
}
