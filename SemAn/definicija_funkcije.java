public class definicija_funkcije extends Node {

	public definicija_funkcije(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 66 Zadnji provjeri : provjeri(<slozena_naredba>) uz parametre
	 * funkcije koristeci <lista_parametara>.tipovi i <lista_parametara>.imena.
	 * -->nije gotov
	 */
	@Override
	public void provjeri() {
		Node lista_parametara = child.get(3);
		Node IDN = child.get(1);
		String imeFunkcije = ((UniformniZnak) child.get(1)).value;
		if (lista_parametara.getName().equals("KR_VOID")) {
			Node ime_tipa = child.get(0);
			ime_tipa.provjeri();
			if (ime_tipa.getType().equals(new Tip(TipBasic.const_T))) {
				writeErrorMessage();
			}
			/*
			 * ako postoji deklaracija imena IDN.ime (child[1]) u globalnom
			 * djelokrugu onda je pripadni tip te deklaracije funkcija(void →
			 * <ime_tipa>.tip)
			 */
			if (funcmem.contains((imeFunkcije))) {
				if (funcmem.get(IDN.getName()).getImplementirana()) {
					writeErrorMessage();
				}
				/*
				 * ako postoji deklaracija imena IDN.ime (child[1]) u globalnom
				 * djelokrugu onda je pripadni tip te deklaracije funkcija(void
				 * → <ime_tipa>.tip)
				 */
				else {
					Function funkcijaKojuProvjeravamo = funcmem
							.get(imeFunkcije);
					if (!funkcijaKojuProvjeravamo.getTipFunkcije().equals(
							new Tip(child.get(0).getType().getGlavni(), null,
									false, true))) {
						writeErrorMessage();
					}

				}

			}

			/*
			 * zabiljezi definiciju i deklaraciju funkcije
			 */

			funcmem.add(imeFunkcije, new Function(new Tip(child.get(0)
					.getType().getGlavni(), null, false, true), true));
			child.get(5).provjeri();

		} else if (lista_parametara.getName().equals("<lista_parametara>")) {
			Node ime_tipa = child.get(0);
			ime_tipa.provjeri();
			if (ime_tipa.getType().equals(new Tip(TipBasic.const_T))) {
				writeErrorMessage();
			}
			/*
			 * ne postoji prije definirana funkcija imena IDN.ime (child[1])
			 */
			if (funcmem.contains((imeFunkcije))) {
				if (funcmem.get(IDN.getName()).getImplementirana()) {
					writeErrorMessage();
				}
			}
			lista_parametara.provjeri();
			/*
			 * TODONE ako postoji deklaracija imena IDN.ime u globalnom
			 * djelokrugu onda je pripadni tip te deklaracije
			 * funkcija(<lista_parametara>.tipovi → <ime_tipa>.tip)
			 */
			Function funkcijaKojuProvjeravamo = funcmem.get(imeFunkcije);
			if (!funkcijaKojuProvjeravamo.getTipFunkcije().equals(
					new Tip(child.get(3).getType().getGlavni(), null, false,
							true))) {
				writeErrorMessage();

			}

			/*
			 * zabiljezi definiciju i deklaraciju funkcije
			 */
			funcmem.add(imeFunkcije, new Function(new Tip(child.get(0)
					.getType().getGlavni(), null, false, true), true));
			/*
			 * TODO provjeri (<slozena_naredba>) uz parametre funkcije koristeci
			 * <lista_parametara>.tipovi i <lista_parametara>.imena.
			 */
		}
	}

}
