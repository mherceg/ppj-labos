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
		case "odnosni_izraz":
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
			break;
		default:
			break;
		}
	}
}
