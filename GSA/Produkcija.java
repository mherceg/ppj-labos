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

	public Produkcija(Produkcija produkcija) {
		this.ljevoOdTockice = new ArrayList<String>();
		this.desnoOdTockice = new ArrayList<String>();
		this.zapocinje = new ArrayList<String>();
		
		this.left = new String(produkcija.left);
		this.ljevoOdTockice.addAll(produkcija.getLjevoOdTockice());
		this.desnoOdTockice.addAll(produkcija.getDesnoOdTockice());
		this.zapocinje=produkcija.getZapocinje();
	}

	public void setZapocinje(List<String> zapocinje) {
		this.zapocinje.clear();
		this.zapocinje.addAll(zapocinje);
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

	public void setLeft(String left) {
		this.left = left;
	}

	public void setLjevoOdTockice(List<String> ljevoOdTockice) {
		this.ljevoOdTockice = ljevoOdTockice;
	}

	public void setDesnoOdTockice(List<String> desnoOdTockice) {
		this.desnoOdTockice = desnoOdTockice;
	}

	public void ispisi() {
		System.out.print(left + "->");
		if (ljevoOdTockice != null) {
			for (String string : ljevoOdTockice) {
				System.out.print(string);
			}
		}
		System.out.print("*");

		if (desnoOdTockice != null) {
			for (String string : desnoOdTockice) {
				System.out.print(string);
			}
		}
		System.out.println();
	}

}
