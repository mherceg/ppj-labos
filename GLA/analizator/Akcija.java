import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Akcija")
public class Akcija {
	private String regex;
	private String identifikator;
	private boolean noviRed;
	private int vraca;
	private String novoStanje;
	
	
	
	public String getRegex() {
		return regex;
	}
	@XmlElement(name="regex")
	public void setRegex(String regex) {
		this.regex = regex;
	}
	
	public String getIdentifikator() {
		return identifikator;
	}
	@XmlElement(name="identifikator")
	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}
	
	public boolean isNoviRed() {
		return noviRed;
	}
	@XmlElement(name="noviRed")
	public void setNoviRed(boolean noviRed) {
		this.noviRed = noviRed;
	}
	
	public int getVraca() {
		return vraca;
	}
	@XmlElement(name="vraca")
	public void setVraca(int vraca) {
		this.vraca = vraca;
	}
	
	public String getNovoStanje() {
		return novoStanje;
	}
	@XmlElement(name="novoStanje")
	public void setNovoStanje(String novoStanje) {
		this.novoStanje = novoStanje;
	}
	
	
}
