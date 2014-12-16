public class postfiks_izraz extends Node {

	public postfiks_izraz(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Strana 52
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if (childNula.getName().equals("<primarni_izraz>")) {
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			this.characteristics.setlIzraz(childNula.getlIzraz());
			
		} else if (childNula.getName().equals(
				"<" + postfiks_izraz.class.getName() + ">")) {
			if (child.size() == 2) {
				childNula.provjeri();
				if (!childNula.getCharacteristics().islIzraz()
						|| !Provjerinator.tilda(childNula.getType(), new Tip(
								TipBasic.INT))) {
					writeErrorMessage();
				}
				this.setType(new Tip(TipBasic.INT));
				this.characteristics.setlIzraz(false);

			} else if (child.size() == 3) {
				childNula.provjeri();

				// 2. <postfiks_izraz>.tip = funkcija(void ! pov)
				if (!childNula.getType().getPolje().isEmpty()
						|| !childNula.getType().isFunction()) {
					writeErrorMessage();
				}

				// this.characteristics.setType(Tip.pov);
				this.setType(new Tip(childNula.getType().getGlavni()));

				this.characteristics.setlIzraz(false);
			} else if (child.size() == 4) {
				Node childDva = child.get(2);
				if (childDva.getName().equals("<izraz>")) {

					childNula.provjeri();
					// 2. <postfiks_izraz>.tip = niz (X)
					if (!childNula.getType().equals(new Tip(TipBasic.X, true))) {
						writeErrorMessage();
					}
					childDva.provjeri();

					// 4. <izraz>.tip  int
					if (!Provjerinator.tilda(childDva.getType(), new Tip(
							TipBasic.INT))) {
						writeErrorMessage();
					}

					// TODO this.characteristics.setType(Tip.X);
					/*
					 * ne kuzim dal ovdje zeli bas X ili onaj X koji je u niz(X)
					 * ovaj drugi ce bit nesto konkretnije i dat ce se usporedit
					 * za ovo ispod
					 */
					// TODONE?
					this.setType(new Tip(childNula.getType().getGlavni()));

					// TODONE this.characteristics.setlIzraz( l-izraz X !=
					// const(T));
					this.characteristics.setlIzraz(TipBasic.equals(childNula
							.getType().getGlavni(), TipBasic.const_T));

				} else if (childDva.getName().equals("<lista_argumenata>")) {

					childNula.provjeri();
					childDva.provjeri();
					// TODO 3. <postfiks_izraz>.tip = funkcija(params ! pov) i
					// redom po elementima
					// arg-tip iz <lista_argumenata>.tipovi i param-tip iz
					// params vrijedi arg-tip
					//  param-tip
					// TODO this.characteristics.setType(Tip.pov);
					this.characteristics.setlIzraz(false);
				} else {
					System.err.println("Greska kod "
							+ this.getClass().getName() + " za -> "
							+ child.toString());
				}
			} else {
				System.err.println("Greska kod " + this.getClass().getName()
						+ " za -> " + child.toString());
			}
		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}

}
