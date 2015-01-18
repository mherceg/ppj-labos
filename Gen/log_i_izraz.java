
public class log_i_izraz extends Node {

	public log_i_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 60.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		String label=GeneratorKoda.newLabel();
		if(childNula.getName().equals("<bin_ili_izraz>")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+log_i_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			GeneratorKoda.append("POP R0");
			GeneratorKoda.append("AND R0,R0,R0");
			GeneratorKoda.append("CMP R0,0");
			GeneratorKoda.append("JP_EQ "+label);
			//  2. <log_i_izraz>.tip tilda int		
			if(!Provjerinator.tilda(childNula.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			childDva.provjeri();
			GeneratorKoda.append("POP R0");
			GeneratorKoda.append(label,"PUSH R0");
			
			
			//  4. <bin_ili_izraz>.tip  int
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			
			//  this.characteristics.setType(Tip.int);
			this.setType(new Tip(TipBasic.INT));
			this.characteristics.setlIzraz(false);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
