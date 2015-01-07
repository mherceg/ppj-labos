
public class deklaracija extends Node {

	public deklaracija(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * 68
	 * gotov
	 */
	@Override
	public void provjeri() {
		Node ime_tipa = child.get(0);
		Node lista = child.get(1);
		
		ime_tipa.provjeri();
		lista.setType(ime_tipa.getType());
		lista.provjeri();

	}

}
