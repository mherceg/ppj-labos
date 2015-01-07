public class naredba_skoka extends Node {

	public naredba_skoka(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 64.
	 */
	@Override
	public void provjeri() {
		/*
		 * ovo zahtjeva malo dodavanja
		 * 
		 * recimo par statika u Node (isto kao mem) da znamo ako smo i u kojoj
		 * funkciji, ako smo u petlji i sta god jos treba
		 */
		Node nula = child.get(0);
		switch (nula.getName()) {
		case "KR_CONTINUE":
		case "KR_BREAK":
			if (!Node.inLoop()) {
				writeErrorMessage();
			}
			break;
		case "KR_RETURN":
			Node izraz = child.get(1);
			if (izraz.getName().equals("TOCKAZAREZ")) {
				/*
				 * naredba se nalazi unutar funkcije tipa funkcija(params ->
				 * void)
				 */
				if (this.getActiveFuncRetType() != TipBasic.VOID) {
					writeErrorMessage();
				}
			} else {
				izraz.provjeri();
				/*
				 * naredba se nalazi unutar funkcije tipa funkcija(params -> pov
				 * ) i vrijedi <izraz>.tip tilda pov
				 */
				if (this.getActiveFuncRetType() == null
						|| !Provjerinator.tilda(izraz.getType(),
								new Tip(this.getActiveFuncRetType()))) {
					writeErrorMessage();
				}
			}
		}
	}
}
