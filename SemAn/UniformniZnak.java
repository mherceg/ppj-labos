

public class UniformniZnak extends Node {

	public UniformniZnak(String name, boolean lIzraz, Tip type, int red, int brElem, String value) {
		super(name,lIzraz,type,red,brElem);
		if (name.equals("NIZ_ZNAKOVA")){
			this.setType(new Tip(TipBasic.const_CHAR, true));
		}
		if (name.equals("BROJ")){
			this.setType(new Tip(TipBasic.INT));
		}
		if (name.equals("ZNAK")){
			this.setType(new Tip(TipBasic.CHAR));
		}
		this.setValue(value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void provjeri() {
		//ovo treba bit prazno. Ili ce se ovdje ubacit provjera ta Tip.
	}

	
	
	@Override
	public String toString() {
		return getName() + "(" + getRed() + "," + getValue() + ")";
	}

	
}
