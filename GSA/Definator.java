import java.util.LinkedList;
import java.util.List;

public class Definator {
	
	private List<String> zavrsniZnakovi = new LinkedList<String>();
	private List<String> nezavrsniZnakovi = new LinkedList<String>();
	private List<String> sinkronizacijskiZnakovi = new LinkedList<String>();

	public void dodajZavrsnuListu(List<String> list) {
		zavrsniZnakovi = list;
	}

	public void dodajNezavrsnuListu(List<String> list) {
		nezavrsniZnakovi = list;
	}

	public void dodajSinkronizacijskuListu(List<String> list) {
		sinkronizacijskiZnakovi = list;
	}

	public List<String> getZavrsniZnakovi() {
		return zavrsniZnakovi;
	}

	public List<String> getNezavrsniZnakovi() {
		return nezavrsniZnakovi;
	}

	public List<String> getSinkronizacijskiZnakovi() {
		return sinkronizacijskiZnakovi;
	}
	
	

}
