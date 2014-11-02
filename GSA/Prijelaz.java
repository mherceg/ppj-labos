public class Prijelaz {
	private String znak;
	private String novoStanje;

	public Prijelaz(String znak, String novoStanje) {
		this.znak = znak;
		this.novoStanje = novoStanje;
	}

	public String getZnak() {
		return znak;
	}

	public void setZnak(String znak) {
		this.znak = znak;
	}

	public String getNovoStanje() {
		return novoStanje;
	}

	public void setNovoStanje(String novoStanje) {
		this.novoStanje = novoStanje;
	}

}
