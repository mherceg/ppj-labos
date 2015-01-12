
public class naredba_grananja extends Node {
	
	public naredba_grananja(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}
	/**
	 * Str 63f
	 */
	@Override
	public void provjeri() {
		int childCount = child.size();
		
		if(childCount == 5){
			Node childDva = child.get(2);
			
			childDva.provjeri();
			String label = GeneratorKoda.newLabel();
			GeneratorKoda.append("POP R0");
			GeneratorKoda.append("CMP R0, 0");
			GeneratorKoda.append("JP_NE " + label);
			//<izraz>.tip tilda int
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			child.get(4).provjeri();
			GeneratorKoda.appendBez(label);
		}
		else if(childCount == 7){
			String labelelse = GeneratorKoda.newLabel();
			String labelout = GeneratorKoda.newLabel();
			Node childDva = child.get(2);			
			childDva.provjeri();
			GeneratorKoda.append("POP R0");
			GeneratorKoda.append("CMP R0, 0");
			GeneratorKoda.append("JP_NE " + labelelse);
			//<izraz>.tip tilda int
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			child.get(4).provjeri();
			GeneratorKoda.append("JP " + labelout);
			GeneratorKoda.appendBez(labelelse);
			child.get(6).provjeri();
			GeneratorKoda.appendBez(labelout);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
