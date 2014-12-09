public class Function {

	private Tip tipFunkcije;
	private Boolean implementirana;

	public Function(Tip tipFunkcije, Boolean implementirana) {
		super();
		this.tipFunkcije = tipFunkcije;
		this.implementirana = implementirana;
	}

	public Tip getTipFunkcije() {
		return tipFunkcije;
	}

	public void setTipFunkcije(Tip tipFunkcije) {
		this.tipFunkcije = tipFunkcije;
	}

	public Boolean getImplementirana() {
		return implementirana;
	}

	public void setImplementirana(Boolean implementirana) {
		this.implementirana = implementirana;
	}

}
