
import java.util.ArrayList;
import java.util.List;

public class Produkcija {

	private String left;
	private List<String> ljevoOdTockice;
	private List<String> desnoOdTockice;
	
	private List<String> zapocinje;

	private int redniBrojPojavljivanja;
	
	public Produkcija(String nezavrsni, List<String> desnoOdTockice, int rednibrojPojavljivanja) {

		this.left = nezavrsni;
		this.ljevoOdTockice = new ArrayList<String>();
		this.desnoOdTockice = new ArrayList<String>();
		this.zapocinje = new ArrayList<String>();
		this.redniBrojPojavljivanja=rednibrojPojavljivanja;
		this.desnoOdTockice.addAll(desnoOdTockice);

	}

	public Produkcija(Produkcija produkcija) {
		this.ljevoOdTockice = new ArrayList<String>();
		this.desnoOdTockice = new ArrayList<String>();
		this.zapocinje = new ArrayList<String>();

		this.left = new String(produkcija.left);
		this.ljevoOdTockice.addAll(produkcija.getLjevoOdTockice());
		this.desnoOdTockice.addAll(produkcija.getDesnoOdTockice());
		this.zapocinje.addAll(produkcija.zapocinje);
		this.redniBrojPojavljivanja=produkcija.getRedniBrojPojavljivanja();
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
		return sb.toString() + "," + zapocinje.toString() + " <"+redniBrojPojavljivanja+">";
	}

	public void ispisi() {
		System.out.println("Produkcija :" + this.toString());
	}

	public int getRedniBrojPojavljivanja() {
		return redniBrojPojavljivanja;
	}


}
