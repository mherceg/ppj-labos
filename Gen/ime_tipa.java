public class ime_tipa extends Node {

	public ime_tipa(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * str 56.
	 */
	@Override
	public void provjeri() {
		if (child.size() == 1) {
			Node childNula = child.get(0);
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
		} else if (child.size() == 2) {
			Node childJedan = child.get(1);

			// Implementirano da ako je <specifikator_tipa>.tip == void
			// ispisuje gresku

			childJedan.provjeri();
			if (childJedan.getType().equals(new Tip(TipBasic.VOID))) {
				writeErrorMessage();
			}
			this.setType(new Tip(TipBasic.addConst(childJedan.getType().getGlavni())));

		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}
}
