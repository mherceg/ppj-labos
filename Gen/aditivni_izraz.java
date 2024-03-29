
public class aditivni_izraz extends Node {

	public aditivni_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 57.
	 * gotov
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<multiplikativni_izraz>")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<aditivni_izraz>")){
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
			
			if(child.get(1).getName().startsWith("MINUS")){
				GeneratorKoda.append("POP R2");
				GeneratorKoda.append("POP R1");
				
				GeneratorKoda.append("SUB R1,R2,R1");
				GeneratorKoda.append("PUSH R1");
			}else{
				GeneratorKoda.append("POP R2");
				GeneratorKoda.append("POP R1");
				
				GeneratorKoda.append("ADD R1,R2,R1");
				GeneratorKoda.append("PUSH R1");				
			}		
			
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
