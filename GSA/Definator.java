

import java.util.ArrayList;
import java.util.List;

public class Definator {
	private List<String> zavrsniZnakovi = new ArrayList<String>();
	private List<String> nezavrsniZnakovi = new ArrayList<String>();
	private List<String> sinkronizacijskiZnakovi = new ArrayList<String>();

	public void dodajZavrsnuListu(List list) {
		zavrsniZnakovi=list;
	}

	public void dodajNezavrsnuListu(List list) {
		nezavrsniZnakovi=list;
	}

	public void dodajSinkronizacijskuListu(List list) {
		sinkronizacijskiZnakovi=list;
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
