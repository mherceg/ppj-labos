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
			switch (((UniformniZnak)childNula.child.get(0)).getName()) {
			case "PLUS":
				break;
			case "MINUS":
				GeneratorKoda.append("POP R0");
				GeneratorKoda.append("MOVE 0, R1");
				GeneratorKoda.append("SUB R1,R0,R0");
				GeneratorKoda.append("PUSH R0");
				break;
			case "OP_TILDA":
				GeneratorKoda.append("POP R0");
				GeneratorKoda.append("MOVE FFFF, R1");
				GeneratorKoda.append("MOVE 20, R2");
				GeneratorKoda.append("SHL R1, R2, R1");
				GeneratorKoda.append("MOVE FFFF, R2");
				GeneratorKoda.append("ADD R1, R2, R1");
				GeneratorKoda.append("XOR R0, R1, R0");
				GeneratorKoda.append("PUSH R0");
				break;
			case "OP_NEG":
				GeneratorKoda.append("POP R0");
				GeneratorKoda.append("CMP R0,0");
				String zero = GeneratorKoda.newLabel();
				String out = GeneratorKoda.newLabel();
				GeneratorKoda.append("JP_EQ " + zero);
				GeneratorKoda.append("MOVE 1, R0");
				GeneratorKoda.append("JP " + out);
				GeneratorKoda.append(zero, "MOVE 0, R0");
				GeneratorKoda.append(out, "PUSH R0");
				break;

			default:
				break;
			}
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
				
				/**
				 * ++x, --x
				 */
				String location = mem.getWithLocation(child.get(1).getValue()).getLocation();
				GeneratorKoda.append("LOAD R0, ("+ location + ")");
				
				switch (child.get(0).getName()) {
				case "OP_INC":
					GeneratorKoda.append("ADD R0, 1, R0");
					break;
				case "OP_DEC":
					GeneratorKoda.append("SUB R0, 1, R0");
					break;
				}
				GeneratorKoda.append("PUSH R0");
				GeneratorKoda.append("STORE R0, ("+ location + ")");
		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}

}
