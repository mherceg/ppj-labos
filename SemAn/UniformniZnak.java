import java.util.List;


public class UniformniZnak extends Node {

	String value;
	public UniformniZnak(String name, boolean lIzraz, Tip type, int red, int brElem, String value) {
		super(name,lIzraz,type,red,brElem);
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void provjeri() {
		//ovo treba bit prazno. Ili ce se ovdje ubacit provjera ta Tip.
	}

}
