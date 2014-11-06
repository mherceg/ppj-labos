public class Prijelaz implements Comparable<Prijelaz> {
	private String znak;
	private Stanje novoStanje;

	public Prijelaz(String znak, Stanje novoStanje) {
		this.znak = znak;
		this.novoStanje = novoStanje;
	}

	public Prijelaz(Prijelaz prijelaz) {
		this.znak = new String(prijelaz.znak);
		this.novoStanje = prijelaz.getNovoStanje();
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Prijelaz)) {
			return false;
		}
		return this.znak.equals(((Prijelaz) obj).getZnak())
				&& this.novoStanje.equals(((Prijelaz) obj).getNovoStanje());
	}

	@Override
	public int compareTo(Prijelaz o) {
		return this.getNovoStanje().getImeStanja()
				.compareTo(o.getNovoStanje().getImeStanja());
	}

	public String toString() {
		String string;

		string = "Prijelaz: za znak: " + znak + " Prelazi u:"
				+ this.novoStanje.getImeStanja();

		return string;
	}

}
