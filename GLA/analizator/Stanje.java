import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Stanje")
public class Stanje {
	private String ime;
	private List<Akcija> akcija;
	
	public String getIme() {
		return ime;
	}
	

	@XmlElement(name="ime")
	public void setIme(String ime) {
		this.ime = ime;
	}

	public List<Akcija> getAkcija() {
		return akcija;
	}
	@XmlElement(name="Akcija")
	public void setAkcija(List<Akcija> akcija) {
		this.akcija = akcija;
	}
}
