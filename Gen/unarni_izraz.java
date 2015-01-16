public class unarni_izraz extends Node {

	public unarni_izraz(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * str 54 gotov
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if (childNula.getName().equals("<postfiks_izraz>")) {
			childNula.provjeri();
			
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			if(this.value!=null && !this.value.startsWith("\"")){//moze vratit il ime IDN il niz znakova oblika "niz"
				//IDN
				VariableMemory<Tip>.MemoryElement lokacija = Node.mem.getWithLocation(value);
				GeneratorKoda.append("LOAD R0, (" + lokacija.getLocation() + ")");
				GeneratorKoda.append("PUSH R0");
			}
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
				Node childJedan = child.get(1);
				childJedan.provjeri();

				// 2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip  int
				if (!childJedan.getCharacteristics().islIzraz()
						|| !Provjerinator.tilda(childJedan.getType(), new Tip(
								TipBasic.INT))) {
					writeErrorMessage();
				}

				// this.characteristics.setType(Tip.int);
				this.setType(new Tip(TipBasic.INT));
				this.characteristics.setlIzraz(false);
		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}

}
