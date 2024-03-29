public class izravni_deklarator extends Node {

	public izravni_deklarator(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * 70
	 */
	@Override
	public void provjeri() {
		Node nula = child.get(0);
		String name = nula.getValue();
		if (child.size() == 1) {
			// 1.
			if (this.getType().equals(new Tip(TipBasic.VOID))) {
				writeErrorMessage();
			}

			// 2
			if (mem.containsAtThisLevel(name)) {
				writeErrorMessage();
			}
			// 3
			mem.add(name, this.getType());

		} else {
			Node dva = child.get(2);
			
			if (dva.getName().equals("BROJ")) {
				
				// 1
				if (this.getType().equals(new Tip(TipBasic.VOID))) {
					writeErrorMessage();
				}
				// 2
				if (mem.containsAtThisLevel(name)) {
					writeErrorMessage();
				}
				int arraySize = 0;
				try{					
					arraySize = Integer
							.parseInt( child.get(2).getValue());
				}
				catch (NumberFormatException e){
					writeErrorMessage();
				}
				// 3
				if (arraySize <= 0 || arraySize > 1024) {
					writeErrorMessage();
				}
				this.getType().setArray(true); // povratni tip
				// 4
				this.getCharacteristics().setBrElem(Integer.parseInt(dva.getValue()));
				mem.add(name, this.getType());

			} else if (dva.getName().equals("KR_VOID")) {
				/*
				 * 1. ako je IDN.ime deklarirano u lokalnom djelokrugu, tip
				 * prethodne deklaracije je jednak funkcija(void ! ntip)
				 * 2.zabiljezi deklaraciju IDN.ime s odgovarajucim tipom ako
				 * ista funkcija vec nije deklarirana u lokalnom djelokrugu
				 */
				String imeFunkcije =  child.get(0).getValue();
				if (funcmem.containsAtThisLevel(imeFunkcije)) {
					if (!(funcmem.get(imeFunkcije).getTipFunkcije()
							.equals(new Tip(this.getType().getGlavni(), null,
									false, true)))) {
						
						writeErrorMessage();
					}
				} else {
					funcmem.add(imeFunkcije, new Function(imeFunkcije,new Tip(this
							.getType().getGlavni(), null, false, true), false));
				}

			} else if (dva.getName().equals("<lista_parametara>")) {
				dva.provjeri();
				String imeFunkcije = ((UniformniZnak) child.get(0)).getValue();
				if (funcmem.containsAtThisLevel(imeFunkcije)) {
					if (!(funcmem.get(imeFunkcije).getTipFunkcije()
							.equals(new Tip(this.getType()
									.getGlavni(), child.get(2).getTypes(),
									false, true)))) {
						writeErrorMessage();
					}
				} else {
					funcmem.add(imeFunkcije, new Function(imeFunkcije,new Tip(this
							.getType().getGlavni(), child.get(2).getTypes(),
							false, true), false));
				}

			}
		}
	}

}
