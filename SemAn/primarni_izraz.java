
public class primarni_izraz extends Node {

	public primarni_izraz(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * Strana 51
	 * gotov
	 */
	@Override
	public void provjeri() {
		if (child.size() == 1) {
			Node childNula = child.get(0);
			String value =  childNula.getValue();
			if (childNula.getName().equals("IDN")) {
				if (!mem.contains(value)&&!funcmem.contains(value)) {
					writeErrorMessage();
				}
				if (mem.contains(value)){					
					this.setType(mem.get(value));
				}
				else{
					this.setType(funcmem.get(value).getTipFunkcije());
				}
				this.characteristics.setlIzraz(childNula.getCharacteristics()
						.islIzraz());
			} else if (childNula.getName().equals("BROJ")) {
				if (!Provjerinator.rasponInt(value)) {
					writeErrorMessage();
				}
				this.setType(new Tip(TipBasic.INT));
				this.characteristics.setlIzraz(false);
				
			} else if (childNula.getName().equals("ZNAK")) {
				if (!Provjerinator.znakOK(value)) {
					writeErrorMessage();
				}

				this.setType(new Tip(TipBasic.CHAR));
				this.characteristics.setlIzraz(false);
			
			} else if (childNula.getName().equals("NIZ_ZNAKOVA")) {
				if (!Provjerinator.konstNizZnakovaOK(value)) {
					writeErrorMessage();
				}
				this.setType(new Tip(TipBasic.const_CHAR, true));
				this.setValue(value);
				this.characteristics.setlIzraz(false);
			
			} else {
				System.err.println("Greska kod " + this.getClass().getName()
						+ " za -> " + child.toString());
			}
		} else if (child.size() == 3) {
			Node childJedan = child.get(1);
			childJedan.provjeri();
			this.characteristics.setType(childJedan.getType());
			this.characteristics.setlIzraz(childJedan.getlIzraz());
		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}

}
