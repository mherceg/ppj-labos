import java.util.List;

public class unarni_operator extends Node {

	public unarni_operator(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 55.
	 * gotov
	 */
	@Override
	public void provjeri() {
		Node nula = child.get(0);
		switch (nula.getName()) {
		case "<postfiks_izraz>":
			nula.provjeri();
			this.setType(nula.getType());
			this.characteristics
					.setlIzraz(nula.getCharacteristics().islIzraz());
			break;
		case "OP_INC":
		case "OP_DEC":
			Node unarni_izraz = child.get(1);
			unarni_izraz.provjeri();
			if (!unarni_izraz.getCharacteristics().islIzraz()
					|| !Provjerinator.tilda(unarni_izraz.getType(), new Tip(
							TipBasic.INT))) {
				writeErrorMessage();
			}
			this.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
			break;

		case "<unarni_operator>":
			Node cast_izraz = child.get(1);
			cast_izraz.provjeri();
			if (!Provjerinator.tilda(cast_izraz.getType(),
					new Tip(TipBasic.INT))) {
				
				writeErrorMessage();
			}
			this.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
			break;
		}

	}

}
