import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="Akcija")
public class Akcija {
	private Tip akcija;
	private String left; // stanje
	private List<String> right;
	
	public Akcija(){};
	
	public Akcija (Tip akcija){
		this.akcija = akcija;
	}
	
	public Akcija(Tip akcija, String left, List<String> right){
		this.akcija = akcija;
		this.left = left;
		this.right = right;
	}

	public Tip getAkcija() {
		return akcija;
	}


	@XmlElement(name="akcija")
	public void setAkcija(Tip akcija) {
		this.akcija = akcija;
	}

	public String getLeft() {
		return left;
	}

	@XmlElement(name="left")
	public void setLeft(String left) {
		this.left = left;
	}

	public List<String> getRight() {
		return right;
	}

	@XmlElement(name="right")
	public void setRight(List<String> right) {
		this.right = right;
	}

}
