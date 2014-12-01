import java.util.ArrayList;
import java.util.List;

public class GramatickaProdukcija {
	private String lijevaStrana;
	private List<String> desnaStrana = new ArrayList<String>();
	private List<Integer> redosljedDesneStrane = new ArrayList<Integer>();

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

	public void dodajNoviDesniIzraz(String izraz, int brojProdukcije) {
		desnaStrana.add(izraz);
		redosljedDesneStrane.add(brojProdukcije);
	}
	
	public List<Integer> getRedosljedDesnihStrana(){
		return redosljedDesneStrane;
	}

}
