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
		
		if (childDva.getName().equals("<izraz>")) {
			String labelbegin = GeneratorKoda.newLabel();
			String labelout = GeneratorKoda.newLabel();
			Node childCetiri = child.get(4);
			GeneratorKoda.appendBez(labelbegin);
			childDva.provjeri();
			GeneratorKoda.append("POP R0");
			GeneratorKoda.append("CMP R0, 0");
			GeneratorKoda.append("JP_NE " + labelout);
			
			// 2. <izraz>.tip tilda int
			if (!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))) {
				writeErrorMessage();
			}
			Node.enterLoop();
			childCetiri.provjeri();
			Node.exitLoop();
			GeneratorKoda.append("JP " + labelout);
			GeneratorKoda.appendBez(labelout);
			
			
		} else if (childDva.getName().equals("<izraz_naredba>")) {
			int childCount = child.size();
			if (childCount == 6) {
				String labelCheck = GeneratorKoda.newLabel();
				String labelCommand = GeneratorKoda.newLabel();
				String labelOut = GeneratorKoda.newLabel();
				Node childTri = child.get(3);
				Node childPet = child.get(5);
				childDva.provjeri();
				GeneratorKoda.appendBez(labelCheck);
				childTri.provjeri();
				GeneratorKoda.append("POP R0");
				GeneratorKoda.append("CMP R0,0");
				GeneratorKoda.append("JP_NE " + labelOut);
				GeneratorKoda.append("JP " + labelCommand);
				// 3. <izraz_naredba>2.tip tilda int
				if (!Provjerinator.tilda(childTri.getType(), new Tip(
						TipBasic.INT))) {
					writeErrorMessage();
				}
				Node.enterLoop();
				GeneratorKoda.appendBez(labelCommand);
				childPet.provjeri();
				GeneratorKoda.append("JP " + labelCheck);
				Node.exitLoop();
				GeneratorKoda.appendBez(labelOut);
				
			} else if (childCount == 7) {
				String labelCheck = GeneratorKoda.newLabel();
				String labelStep = GeneratorKoda.newLabel();
				String labelCommand = GeneratorKoda.newLabel();
				String labelOut = GeneratorKoda.newLabel();
				Node childTri = child.get(3);
				Node izraz = child.get(4);
				Node naredba = child.get(6);
				childDva.provjeri();
				GeneratorKoda.appendBez(labelCheck);
				childTri.provjeri();
				GeneratorKoda.append("POP R0");
				GeneratorKoda.append("CMP R0,0");
				GeneratorKoda.append("JP_NE " + labelOut);
				GeneratorKoda.append("JP " + labelCommand);
				GeneratorKoda.appendBez(labelStep);
				// <izraz_naredba>2.tip tilda int
				if (!Provjerinator.tilda(childTri.getType(), new Tip(
						TipBasic.INT))) {
					writeErrorMessage();
				}
				izraz.provjeri();
				GeneratorKoda.append("JP " + labelCheck);
				
				Node.enterLoop();
				GeneratorKoda.appendBez(labelCommand);
				naredba.provjeri();
				GeneratorKoda.append("JP " + labelStep);
				Node.exitLoop();
			}
		}
		

	}

}
