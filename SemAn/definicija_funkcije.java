import java.util.Iterator;

public class definicija_funkcije extends Node {

	public definicija_funkcije(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 66 Zadnji provjeri : provjeri(<slozena_naredba>) uz parametre
	 * funkcije koristeci <lista_parametara>.tipovi i <lista_parametara>.imena.
	 * -->nije gotov
	 */
	@Override
	public void provjeri() {
		Node lista_parametara = child.get(3);
		Node IDN = child.get(1);
		Node ime_tipa = child.get(0);
		String imeFunkcije = ((UniformniZnak) child.get(1)).getValue();

		if (lista_parametara.getName().equals("KR_VOID")) {

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
				if (funcmem.get(imeFunkcije).getImplementirana()) {
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
							new Tip(ime_tipa.getType().getGlavni(), null,
									false, true))) {
						writeErrorMessage();
					}

				}

			}

			/*
			 * zabiljezi definiciju i deklaraciju funkcije
			 */

			funcmem.add(imeFunkcije, new Function(new Tip(ime_tipa.getType()
					.getGlavni(), null, false, true), true));

			mem.goDown();
			Node.activeFuncRetType = ime_tipa.getType().getGlavni();
			child.get(5).provjeri();
			mem.goUp();
		} else if (lista_parametara.getName().equals("<lista_parametara>")) {

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

			Function funkcijaKojuProvjeravamo = funcmem.get(imeFunkcije);
			// TODO check this
			if (funkcijaKojuProvjeravamo != null
					&& !funkcijaKojuProvjeravamo.getTipFunkcije().equals(
							new Tip(child.get(0).getType().getGlavni(), child
									.get(2).getTypes(), false, true))) {
				writeErrorMessage();

			}

			/*
			 * zabiljezi definiciju i deklaraciju funkcije
			 */
			funcmem.add(imeFunkcije, new Function(new Tip(child.get(0)
					.getType().getGlavni(), lista_parametara.getTypes(), false, true), true));
			/*
			 * provjeri (<slozena_naredba>) uz parametre funkcije koristeci
			 * <lista_parametara>.tipovi i <lista_parametara>.imena.
			 */
			mem.goDown();
			Iterator<Tip> tipIter = child.get(3).getTypes().iterator();
			Iterator<String> imeIter = child.get(3).getNames().iterator();

			while (tipIter.hasNext() && imeIter.hasNext()) {
				mem.add(imeIter.next(), tipIter.next());
			}
			Node.activeFuncRetType = child.get(0).getType().getGlavni();
			child.get(5).provjeri(); // provjeri (slozena naredba)
			mem.goUp();
		}
	}

}
