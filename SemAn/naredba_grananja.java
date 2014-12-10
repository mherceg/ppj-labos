
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
			//<izraz>.tip tilda int
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			child.get(4).provjeri();
		}
		else if(childCount == 7){
			Node childDva = child.get(2);
			
			childDva.provjeri();
			//<izraz>.tip tilda int
			if(!Provjerinator.tilda(childDva.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			child.get(4).provjeri();
			child.get(6).provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
