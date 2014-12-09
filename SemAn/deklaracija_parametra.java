

public class deklaracija_parametra extends Node {

	public deklaracija_parametra(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 67.
	 */
	@Override
	public void provjeri() {
		int childCount = child.size();
		Node childNula = child.get(0);
		if(childCount == 2){
			childNula.provjeri();
			if(childNula.getType().equals(new Tip(TipBasic.VOID))){
				writeErrorMessage();
			}
			this.characteristics.setType(childNula.getType());
			this.characteristics.setName(((UniformniZnak)child.get(1)).value);
		}
		else if(childCount == 4){
			childNula.provjeri();
			if(childNula.getType().equals(new Tip(TipBasic.VOID))){
				writeErrorMessage();
			}
			this.characteristics.setType(childNula.getType());
			this.characteristics.getType().setArray(true);
			this.characteristics.setName(((UniformniZnak)child.get(1)).value);
		}	
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}

	}

}
