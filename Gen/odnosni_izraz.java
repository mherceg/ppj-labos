public class odnosni_izraz extends Node {

	public odnosni_izraz(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	@Override
	public void provjeri() {
		Node nula = child.get(0);
		switch (nula.getName()) {
		case "<aditivni_izraz>":
			nula.provjeri();
			this.setType(nula.getType());
			this.characteristics
					.setlIzraz(nula.getCharacteristics().islIzraz());
			this.setValue(nula.getValue());
			break;
		case "<odnosni_izraz>":
			nula.provjeri();
			if (!Provjerinator.tilda(nula.getType(), new Tip(TipBasic.INT))) {
				writeErrorMessage();
			}
			Node dva = child.get(2);
			dva.provjeri();
			if (!Provjerinator.tilda(dva.getType(), new Tip(TipBasic.INT))) {
				writeErrorMessage();
			}
			this.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
			String one = GeneratorKoda.newLabel();
			String two = GeneratorKoda.newLabel();
			GeneratorKoda.append("POP R0");
			GeneratorKoda.append("POP R1");
			GeneratorKoda.append("CMP R1,R0");
			switch(child.get(1).getName()){
			case "OP_LT":
				GeneratorKoda.append("JP_SLT " + one);
				break;
			case "OP_GT":
				GeneratorKoda.append("JP_SGT " + one);
				break;
			case "OP_LTE":
				GeneratorKoda.append("JP_SLE " + one);
				break;
			case "OP_GTE":
				GeneratorKoda.append("JP_SGE " + one);
				break;
			}
			
			GeneratorKoda.append("MOVE 0, R0");
			GeneratorKoda.append("JP " + two);
			GeneratorKoda.append(one, "MOVE 1, R0");
			GeneratorKoda.append(two, "PUSH R0");
			break;
		default:
			break;
		}
	}
}
