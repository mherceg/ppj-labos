import java.util.ArrayList;
import java.util.List;


public class Definicija {
	private List<Stanje> stanje;

	Definicija(){
		stanje = new ArrayList<>();
	}
	
	public List<Stanje> getStanje() {
		return stanje;
	}

	public void setStanje(List<Stanje> stanje) {
		this.stanje = stanje;
	}
	
}
