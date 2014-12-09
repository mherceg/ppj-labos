import java.util.List;

public class vanjska_deklaracija extends Node {

	public vanjska_deklaracija(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 65
	 * 
	 * Ovo su sve upute:
	 * 
	 * Nezavrsni znak <vanjska_deklaracija> generira ili definiciju funkcije
	 * (znak <definicija_funkcije>) ili deklaraciju varijable ili funkcije (znak
	 * <deklaracija>). Obje produkcije su jediniˇcne i u obje se provjeravaju
	 * pravila u podstablu kojem je znak s desne strane korijen.
	 * 
	 * 
	 */
	@Override
	public void provjeri() {

		Node nula = this.child.get(0);
		nula.provjeri();
		this.setType(nula.getType());

	}

}
