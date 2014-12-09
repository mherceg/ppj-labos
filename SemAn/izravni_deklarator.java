
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
		String name = ((UniformniZnak) nula).value;
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
				//2
				if (mem.containsAtThisLevel(name)) {
					writeErrorMessage();
				}
				int arraySize = Integer
						.parseInt(((UniformniZnak) child.get(2)).value);
				//3
				if (arraySize <= 0 || arraySize > 1024) {
					writeErrorMessage();
				}
				this.getType().setArray(true); // povratni tip
				//4
				mem.add(name, this.getType());
				
				
			} else if (dva.getName().equals("KR_VOID")) {
				//TODO neznam sta sa funkcijama
			} else if (dva.getName().equals("<lista_parametara>")) {
				//TODO neznam sta sa funkcijama
			}
		}
	}

}
