
public class bin_xili_izraz extends Node {

	public bin_xili_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 59
	 * gotov
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<bin_i_izraz>")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+bin_xili_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			if(!Provjerinator.tilda(childNula.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			childDva.provjeri();
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			this.characteristics.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
			
			GeneratorKoda.append("POP R2");
			GeneratorKoda.append("POP R1");
			
			GeneratorKoda.append("XOR R1,R2,R3");
			GeneratorKoda.append("PUSH R3");
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
