import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Definicija")
public class Definicija {
	private List<Stanje> stanje;

	
	
	public List<Stanje> getStanje() {
		return stanje;
	}
	@XmlElement(name="Stanje")
	public void setStanje(List<Stanje> stanje) {
		this.stanje = stanje;
	}
	
}
