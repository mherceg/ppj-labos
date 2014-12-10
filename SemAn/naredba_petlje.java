public class naredba_petlje extends Node {

	public naredba_petlje(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * Str 64 ako petlje nemaju svoje lokalne varijable onda izbrisati spustanje
	 * i podazanje mem
	 */
	@Override
	public void provjeri() {
		Node childDva = child.get(2);
		mem.goDown();
		if (childDva.getName().equals("<izraz>")) {
			Node childCetiri = child.get(4);
			childDva.provjeri();

			// 2. <izraz>.tip tilda int
			if (!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))) {
				writeErrorMessage();
			}
			Node.enterLoop();
			childCetiri.provjeri();
			Node.exitLoop();
			
		} else if (childDva.getName().equals("<izraz_naredba>")) {
			int childCount = child.size();
			if (childCount == 6) {
				Node childTri = child.get(3);
				Node childPet = child.get(5);
				childDva.provjeri();
				childTri.provjeri();
				// 3. <izraz_naredba>2.tip tilda int
				if (!Provjerinator.tilda(childTri.getType(), new Tip(
						TipBasic.INT))) {
					writeErrorMessage();
				}
				Node.enterLoop();
				childPet.provjeri();
				Node.exitLoop();
				
			} else if (childCount == 7) {
				Node childTri = child.get(3);
				Node izraz = child.get(4);
				Node naredba = child.get(6);
				childDva.provjeri();
				childTri.provjeri();
				// <izraz_naredba>2.tip tilda int
				if (!Provjerinator.tilda(childTri.getType(), new Tip(
						TipBasic.INT))) {
					writeErrorMessage();
				}
				izraz.provjeri();

				Node.enterLoop();
				naredba.provjeri();
				Node.exitLoop();
			}
		}
		mem.goUp();
	}

}
