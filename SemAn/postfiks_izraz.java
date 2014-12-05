import java.util.List;

public class postfiks_izraz extends Node {

	public postfiks_izraz(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Strana 52
	 */
	@Override
	public void provjeri() {
		Node childnula = child.get(0);
		if (childnula.getName().equals("<primarni_izraz>")) {
			childnula.provjeri();
			this.characteristics.setType(childnula.getType());
			this.characteristics.setlIzraz(childnula.getlIzraz());

		}

		if (childnula.getName().equals("<postfiks_izraz>")) {
			Node childdva = child.get(2);
			childnula.provjeri();
			this.characteristics.addType(Tip.nizOdX);
			childdva.provjeri();
			// TODO ne znam jos

		}
	}

}
