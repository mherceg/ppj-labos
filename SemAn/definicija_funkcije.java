
public class definicija_funkcije extends Node {

	public definicija_funkcije(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 66
	 */
	@Override
	public void provjeri() {
		Node lista_parametara = child.get(3);
		if (lista_parametara.getName().equals("KR_VOID")) {
			Node ime_tipa = child.get(0);
			ime_tipa.provjeri();
			if (ime_tipa.getType().equals(new Tip(TipBasic.const_T))) {
				writeErrorMessage();
			}
			/*
			 * TODO ako postoji deklaracija imena IDN.ime (child[1]) u globalnom
			 * djelokrugu onda je pripadni tip te deklaracije funkcija(void →
			 * <ime_tipa>.tip)
			 */
			/*
			 * TODO zabiljezi definiciju i deklaraciju funkcije
			 */
			child.get(5).provjeri();

		} else if (lista_parametara.getName().equals("<lista_parametara>")) {
			Node ime_tipa = child.get(0);
			ime_tipa.provjeri();
			if (ime_tipa.getType().equals(new Tip(TipBasic.const_T))) {
				writeErrorMessage();
			}
			/*
			 * TODO ne postoji prije definirana funkcija imena IDN.ime
			 * (child[1])
			 */
			lista_parametara.provjeri();
			/*
			 * TODO ako postoji deklaracija imena IDN.ime u globalnom djelokrugu
			 * onda je pripadni tip te deklaracije
			 * funkcija(<lista_parametara>.tipovi → <ime_tipa>.tip)
			 */
			/*
			 * TODO zabiljezi definiciju i deklaraciju funkcije
			 */
			/*
			 * TODO provjeri (<slozena_naredba>) uz parametre funkcije koristeci
			 * <lista_parametara>.tipovi i <lista_parametara>.imena.
			 */
		}
	}

}
