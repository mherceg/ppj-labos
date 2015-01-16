public class init_deklarator extends Node {

	public init_deklarator(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * 69 gotov? ntip
	 */
	@Override
	public void provjeri() {

		Node nula = child.get(0);
		if (child.size() == 1) {
			nula.setType(this.getType()); // ntip
			nula.provjeri();

			if (nula.getType().equals(new Tip(TipBasic.const_T))
					|| nula.getType().equals(new Tip(TipBasic.const_T, true))) {
				writeErrorMessage();
			}
		} else if (child.size() == 3) {
			nula.setType(this.getType()); // ntip
			nula.provjeri();
			
			Node inici = child.get(2);
			inici.provjeri();

			if (nula.getType().equals(new Tip(TipBasic.T))
					|| nula.getType().equals(new Tip(TipBasic.const_T))) {
				if (!Provjerinator
						.tilda(inici.getType(),
								new Tip(TipBasic.stripConst(nula.getType()
										.getGlavni())))) {
					
					writeErrorMessage();
				}
			// nije niz, radi u skladu s tim
				
				GeneratorKoda.append("POP R0 ");
				GeneratorKoda.append("STORE R0, ("+nula.getValue()+")");
					
			} else if (nula.getType().equals(new Tip(TipBasic.T, true))
					|| nula.getType().equals(new Tip(TipBasic.const_T, true))) {
				
				if (inici.getCharacteristics().getBrElem() > nula
						.getCharacteristics().getBrElem()) {
				
					writeErrorMessage();
				}
				for (Tip tip : inici.getTypes()) {
					if (!Provjerinator.tilda(tip,
							new Tip(TipBasic.stripConst(nula.getType()
									.getGlavni())))) {
						
						writeErrorMessage();
					}
				}
				
				// TODO 4 dobili smo niz znakova nakon =, sta sad?
			} else {
				writeErrorMessage();
			}
		}
	}

}
