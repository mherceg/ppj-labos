import java.util.ArrayList;
import java.util.List;

public class Definator {
	
	private List<String> zavrsniZnakovi = new ArrayList<String>();
	private List<String> nezavrsniZnakovi = new ArrayList<String>();
	private List<String> sinkronizacijskiZnakovi = new ArrayList<String>();

	public void dodajZavrsnuListu(List<String> list) {
		zavrsniZnakovi = list;
	}

	public void dodajNezavrsnuListu(List<String> list) {
		nezavrsniZnakovi = list;
	}

	public void dodajSinkronizacijskuListu(List<String> list) {
		sinkronizacijskiZnakovi = list;
	}
	
	

}
