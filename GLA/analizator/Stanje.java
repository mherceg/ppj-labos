import java.util.ArrayList;
import java.util.List;


public class Stanje {
	private String ime;
	private List<Akcija> akcija;
	
	Stanje(){
		akcija = new ArrayList<>();
	}
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public List<Akcija> getAkcija() {
		return akcija;
	}
	public void setAkcija(List<Akcija> akcija) {
		this.akcija = akcija;
	}
}
