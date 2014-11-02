public class Prijelaz {
	private String znak;
	private Stanje novoStanje;

	public Prijelaz(String znak, Stanje novoStanje) {
		this.znak = znak;
		this.novoStanje = novoStanje;
	}

	public String getZnak() {
		return znak;
	}

	public void setZnak(String znak) {
		this.znak = znak;
	}

	public Stanje getNovoStanje() {
		return novoStanje;
	}

	public void setNovoStanje(Stanje novoStanje) {
		this.novoStanje = novoStanje;
	}

}
