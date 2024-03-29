import java.util.Iterator;

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
				/**
				 * x++, x--
				 */
				String location = mem.getWithLocation(childNula.getValue())
						.getLocation();
				GeneratorKoda.append("LOAD R0, (" + location + ")");
				GeneratorKoda.append("PUSH R0");
				switch (child.get(1).getName()) {
				case "OP_INC":
					GeneratorKoda.append("ADD R0, 1, R0");
					break;
				case "OP_DEC":
					GeneratorKoda.append("SUB R0, 1, R0");
					break;
				}
				GeneratorKoda.append("STORE R0, (" + location + ")");

				this.setType(new Tip(TipBasic.INT));
				this.characteristics.setlIzraz(false);

			} else if (child.size() == 3) {
				childNula.provjeri();

				// 2. <postfiks_izraz>.tip = funkcija(void ! pov)
				if (!childNula.getType().getPolje().isEmpty()
						|| !childNula.getType().isFunction()) {
					writeErrorMessage();
				}
				GeneratorKoda.append("PUSH R4");
				GeneratorKoda.append("PUSH R5");
				GeneratorKoda.append("MOVE R7, R5");
				GeneratorKoda.append("CALL " + childNula.value);
				GeneratorKoda.append("POP R5");
				GeneratorKoda.append("POP R4");
				GeneratorKoda.append("PUSH R6");

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
					GeneratorKoda.append("POP R0");

					VariableMemory<Tip>.MemoryElement all = mem
							.getWithLocation(childNula.getValue());

					if (all.getFunctionLevelLabel().equals("")) {
						// globalna
						GeneratorKoda.append("MOVE " + all.getLocation()
								+ ", R1");
						String labelaZaMULPetlju = GeneratorKoda.newLabel();
						String labelaZaIzlazIzMULPetlje = GeneratorKoda.newLabel();

						GeneratorKoda.append("MOVE 0,R3");
						GeneratorKoda.append("MOVE 4,R2");
						GeneratorKoda.append("OR R2,R2,R2");
						GeneratorKoda.append("JR_Z " + labelaZaIzlazIzMULPetlje);
						GeneratorKoda.append(labelaZaMULPetlju, "ADD R3,R0,R3");
						GeneratorKoda.append("SUB R2,1,R2");
						GeneratorKoda.append("JR_NZ " + labelaZaMULPetlju);
						GeneratorKoda.append(labelaZaIzlazIzMULPetlje, "");
						GeneratorKoda.append("SUB R1, R3, R1");
						GeneratorKoda.append("LOAD R0,(R1)");
						GeneratorKoda.append("PUSH R0");
						
						
					} else {
						GeneratorKoda.append("PUSH R5");
						GeneratorKoda.append("LOAD R5,("+ all.getFunctionLevelLabel() + ")");
						
						String labelaZaMULPetlju = GeneratorKoda.newLabel();
						String labelaZaIzlazIzMULPetlje = GeneratorKoda.newLabel();
						
						GeneratorKoda.append("MUL R0, 4 ,R0");
						GeneratorKoda.append("MOVE 0,R3");
						GeneratorKoda.append("MOVE 4,R2");
						GeneratorKoda.append("OR R2,R2,R2");
						GeneratorKoda.append("JR_Z " + labelaZaIzlazIzMULPetlje);
						GeneratorKoda.append(labelaZaMULPetlju, "ADD R3,R0,R3");
						GeneratorKoda.append("SUB R2,1,R2");
						GeneratorKoda.append("JR_NZ " + labelaZaMULPetlju);
						GeneratorKoda.append(labelaZaIzlazIzMULPetlje, "");
						GeneratorKoda.append("MOVE R3,R0");
						
						GeneratorKoda.append("SUB R5, R0 ,R5");
						GeneratorKoda.append("LOAD R0,(R5)");
						GeneratorKoda.append("POP R5");
						GeneratorKoda.append("PUSH R0");
					}

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
					this.characteristics.setlIzraz(!TipBasic.equals(childNula
							.getType().getGlavni(), TipBasic.const_T));

				} else if (childDva.getName().equals("<lista_argumenata>")) {

					GeneratorKoda.append("PUSH R4");
					GeneratorKoda.append("PUSH R5");
					GeneratorKoda.append("MOVE R7, R4");

					childNula.provjeri();
					childDva.provjeri();

					// 3. <postfiks_izraz>.tip = funkcija(params -> pov)
					if (!childNula.getType().isFunction()) {
						writeErrorMessage();
					}

					// redom po elementima
					// arg-tip iz <lista_argumenata>.tipovi i param-tip iz
					// params vrijedi arg-tip tilda param-tip

					Iterator<Tip> argIter = childDva.getTypes().iterator();
					Iterator<Tip> paramIter = childNula.getType().getPolje()
							.iterator();

					while (argIter.hasNext() && paramIter.hasNext()) {
						if (!Provjerinator.tilda(argIter.next(),
								paramIter.next())) {

							writeErrorMessage();
						}
					}
					if (argIter.hasNext() || paramIter.hasNext()) {
						// neki ima vise argumenata
						writeErrorMessage();
					}
					GeneratorKoda.append("MOVE R4, R5");
					GeneratorKoda.append("CALL " + childNula.value);
					GeneratorKoda.append("POP R5");
					GeneratorKoda.append("POP R4");
					GeneratorKoda.append("PUSH R6");

					// this.characteristics.setType(Tip.pov);
					this.setType(new Tip(childNula.getType().getGlavni()));
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
