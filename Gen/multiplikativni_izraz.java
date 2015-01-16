

public class multiplikativni_izraz extends Node{

	public multiplikativni_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 57.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<cast_izraz>")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+multiplikativni_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			//  2. <mutliplikativni_izraz>.tip tilda int
			if(!Provjerinator.tilda(childNula.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			childDva.provjeri();
			//  4. <cast_izraz>.tip  int
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			//  this.characteristics.setType(Tip.int);
			this.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
			
			if(child.get(1).getName().equals("OP_PUTA")){
				// Mnozenje
				GeneratorKoda.append("POP R2");
				GeneratorKoda.append("POP R1");
				
				String labelaZaMULPetlju = GeneratorKoda.newLabel();
				String labelaZaIzlazIzMULPetlje = GeneratorKoda.newLabel();
				
				GeneratorKoda.append("MOVE 0,R3");
				GeneratorKoda.append("OR R2,R2,R2");
				GeneratorKoda.append("JR_Z " + labelaZaIzlazIzMULPetlje);
				GeneratorKoda.append(labelaZaMULPetlju, "ADD R3,R1,R3");
				GeneratorKoda.append("SUB R2,1,R2");
				GeneratorKoda.append("JR_NZ " + labelaZaMULPetlju);
				GeneratorKoda.append(labelaZaIzlazIzMULPetlje, "PUSH R3");
			}
			else if(child.get(1).getName().equals("OP_DIJELI")){
				//dijeljenje  R1/R2 -> od R1 oduzimam R2 n puta
				GeneratorKoda.append("POP R2");
				GeneratorKoda.append("POP R1");
				
				String labelaZaDIVPetlju = GeneratorKoda.newLabel();
				
				GeneratorKoda.append("MOVE -1,R3");
				GeneratorKoda.append(labelaZaDIVPetlju, "ADD R3,1,R3");
				GeneratorKoda.append("SUB R1,R2,R1");
				GeneratorKoda.append("JR_NN " + labelaZaDIVPetlju);
				
				GeneratorKoda.append("PUSH R3");
			}
			else {
				// modulo
				GeneratorKoda.append("POP R2");
				GeneratorKoda.append("POP R1");
				
				String labelaZaDIVPetlju = GeneratorKoda.newLabel();
				
				GeneratorKoda.append("MOVE -1,R3");
				GeneratorKoda.append(labelaZaDIVPetlju, "ADD R3,1,R3");
				GeneratorKoda.append("SUB R1,R2,R1");
				GeneratorKoda.append("JR_NN " + labelaZaDIVPetlju);
				
				GeneratorKoda.append("ADD R1,R2,R1");
				GeneratorKoda.append("PUSH R1");
			}
			
			
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}
	

}
