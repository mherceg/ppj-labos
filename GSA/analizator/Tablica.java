import java.util.HashMap;


public class Tablica {
	private HashMap<String,HashMap<String,Akcija>> tablica;
	private String pocetno;
	
	Tablica(){
		tablica = new HashMap<>();
	}

	public HashMap<String, HashMap<String, Akcija>> getTablica() {
		return tablica;
	}

	public void setTablica(HashMap<String, HashMap<String, Akcija>> tablica) {
		this.tablica = tablica;
	}
	
	public Akcija getAkcija(String i, String j){
		return tablica.get(i).get(j);
	}
	
	public void setAkcija(String i, String j, Akcija a){
		if (!tablica.containsKey(i)){
			tablica.put(i, new HashMap<>());
		}
		tablica.get(i).put(j, a);
	}

	public String getPocetno() {
		return pocetno;
	}

	public void setPocetno(String pocetno) {
		this.pocetno = pocetno;
	}
	
	
}
