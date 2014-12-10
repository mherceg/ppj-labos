public class unarni_izraz extends Node {

	public unarni_izraz(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * str 54
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if (childNula.getName().equals(
				"<" + postfiks_izraz.class.getName() + ">")) {
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		} else if (childNula.getName().equals("<unarni_operator>")) {
			Node childJedan = child.get(1);
			childJedan.provjeri();
			// 2. <cast_izraz>.tip tilda int
			if (!Provjerinator.tilda(childJedan.getType(),
					new Tip(TipBasic.INT))) {
				writeErrorMessage();
			}
			// this.characteristics.setType(Tip.int);
			this.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
		} else if (child.size() == 2) {
			if (childNula.getName().equals("<unarni_izraz>")) {
				Node childJedan = child.get(1);
				childJedan.provjeri();

				// 2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip  int
				if (!childJedan.getCharacteristics().islIzraz()
						|| !Provjerinator.tilda(childJedan.getType(), new Tip(
								TipBasic.INT))) {
					writeErrorMessage();
				}

				//  this.characteristics.setType(Tip.int);
				this.setType(new Tip(TipBasic.INT));
				this.characteristics.setlIzraz(false);
			} else {
				System.err.println("Greska kod " + this.getClass().getName()
						+ " za -> " + child.toString());
			}
		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}

}
