import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Definator {
	
	private List<String> zavrsniZnakovi = new LinkedList<String>();
	private List<String> nezavrsniZnakovi = new LinkedList<String>();
	private ArrayList<String> sinkronizacijskiZnakovi = new ArrayList<String>();

	public void dodajZavrsnuListu(List<String> list) {
		zavrsniZnakovi = list;
	}

	public void dodajNezavrsnuListu(List<String> list) {
		nezavrsniZnakovi = list;
	}

	public void dodajSinkronizacijskuListu(List<String> list) {
		ArrayList<String> x = new ArrayList<>();
		x.addAll(list);
		sinkronizacijskiZnakovi = x;
	}

	public List<String> getZavrsniZnakovi() {
		return zavrsniZnakovi;
	}

	public List<String> getNezavrsniZnakovi() {
		return nezavrsniZnakovi;
	}

	public ArrayList<String> getSinkronizacijskiZnakovi() {
		return sinkronizacijskiZnakovi;
	}
	
	

}
