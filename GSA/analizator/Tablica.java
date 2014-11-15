import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Tablica")
public class Tablica {
	private HashMap<String,Red> tablica;
	private String pocetno;
	
	Tablica(){
		tablica = new HashMap<>();
	}

	public HashMap<String, Red> getTablica() {
		return tablica;
	}
	@XmlElement(name="tablica")
	public void setTablica(HashMap<String, Red> tablica) {
		this.tablica = tablica;
	}
	
	public Akcija getAkcija(String i, String j){
		if (tablica == null) return null;
		if (tablica.get(i) == null) return null;
		return tablica.get(i).getA().get(j);
	}
	public void setAkcija(String i, String j, Akcija a){
		if (!tablica.containsKey(i)){
			tablica.put(i, new Red());
		}
		tablica.get(i).getA().put(j, a);
	}

	public String getPocetno() {
		return pocetno;
	}

	@XmlElement(name="pocetno")
	public void setPocetno(String pocetno) {
		this.pocetno = pocetno;
	}
	
	
}
