import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Definicija")
public class Definicija {
	private List<Stanje> stanje;
	private String pocetno;
	
	Definicija(){
		stanje = new ArrayList<>();
	}
	
	public List<Stanje> getStanje() {
		return stanje;
	}
	@XmlElement(name="Stanje")
	public void setStanje(List<Stanje> stanje) {
		this.stanje = stanje;
	}

	public String getPocetno() {
		return pocetno;
	}
	
	@XmlElement(name="pocetno")
	public void setPocetno(String pocetno) {
		this.pocetno = pocetno;
	}
	
}
