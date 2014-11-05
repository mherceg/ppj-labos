import java.util.LinkedList;
import java.util.List;

public class GramatickaProdukcija {
	private String lijevaStrana;
	private List<String> desnaStrana = new LinkedList<String>();

	public GramatickaProdukcija(String lijevaStrana) {

		this.lijevaStrana = lijevaStrana;
	}

	public GramatickaProdukcija() {

	}

	public String getLijevaStrana() {
		return lijevaStrana;
	}

	public void setLijevaStrana(String lijevaStrana) {
		this.lijevaStrana = lijevaStrana;
	}

	public List<String> getDesnaStrana() {
		return desnaStrana;
	}

	public void setDesnaStrana(List<String> desnaStrana) {
		this.desnaStrana = desnaStrana;
	}

	public void dodajNoviDesniIzraz(String izraz) {
		desnaStrana.add(izraz);
	}

}
