import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Produkcija {

	private String left;
	private List<String> ljevoOdTockice;
	private List<String> desnoOdTockice;

	private List<String> zapocinje;

	public Produkcija(String nezavrsni, List<String> tempList,
			List<String> listZapocinje) {

		this.left = nezavrsni;
		this.ljevoOdTockice = new LinkedList<String>();
		this.desnoOdTockice = new LinkedList<String>();
		this.zapocinje = new LinkedList<String>();

		this.desnoOdTockice.addAll(tempList);
		this.zapocinje.addAll(listZapocinje);

	}

	public Produkcija(Produkcija produkcija) {
		this.ljevoOdTockice = new LinkedList<String>();
		this.desnoOdTockice = new LinkedList<String>();
		this.zapocinje = new LinkedList<String>();

		this.left = new String(produkcija.left);
		this.ljevoOdTockice.addAll(produkcija.getLjevoOdTockice());
		this.desnoOdTockice.addAll(produkcija.getDesnoOdTockice());
		this.zapocinje = produkcija.getZapocinje();
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

	/**
	 * Stvara novu produkciju koja je jednaka trenutnoj osim sto je tockica za
	 * jedno mjesto udesno. Ako je tockica na kraju vraca null.
	 * 
	 * @return nova produkcija
	 */
	public Produkcija createNextProdukcija() {
		if (this.getDesnoOdTockice().isEmpty()) {
			return null;
		}
		Produkcija novaProdukcija = new Produkcija(this);
		novaProdukcija.pomakniTockicu();
		return novaProdukcija;
	}

	/**
	 * Pomice tockicu za jedno mjesto udesno.
	 */
	private void pomakniTockicu() {
		String znakKojiPreskacem = this.desnoOdTockice.get(0);
		this.desnoOdTockice.remove(0);
		this.ljevoOdTockice.add(znakKojiPreskacem);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.left).append("->");
		if (!this.ljevoOdTockice.isEmpty()) {
			for (String string : ljevoOdTockice) {
				sb.append(string);
			}
		}
		sb.append("*");
		if (!this.desnoOdTockice.isEmpty()) {
			for (String string : desnoOdTockice) {
				sb.append(string);
			}
		}
		return "Produkcija: " + sb.toString() + "\n" + "Zapocinje: "
				+ zapocinje.toString() + "\n";
	}

	public void ispisi() {
		System.out.println("Produkcija :" + this.toString());
	}

}
