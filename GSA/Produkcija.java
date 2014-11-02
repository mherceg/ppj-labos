import java.util.ArrayList;
import java.util.List;

public class Produkcija {
	
	private String left;
	private List<String> ljevoOdTockice;
	private List<String> desnoOdTockice;
	
	private List<String> zapocinje;
	
	public Produkcija(String nezavrsni) {
		this.left = nezavrsni;
		this.ljevoOdTockice = new ArrayList<String>();
		this.desnoOdTockice = new ArrayList<String>();

	}	

	public void setZapocinje(List<String> zapocinje) {
		this.zapocinje = zapocinje;
	}

	public String getLeft() {
		return left;
	}
	
	public List<String> getLjevoOdTockice() {
		return ljevoOdTockice;
	}
	
	public List<String> getDesnoOdTockice() {
		return desnoOdTockice;
	}
	
	public List<String> getZapocinje() {
		return zapocinje;
	}
	
}
