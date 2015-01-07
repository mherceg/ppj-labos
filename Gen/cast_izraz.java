
public class cast_izraz extends Node {

	public cast_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 55.
	 * gotov
	 */
	@Override
	public void provjeri() {
		if(child.size() == 1){
			Node childNula = child.get(0);
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.setValue(childNula.getValue());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(child.size() == 4){
			Node childJedan = child.get(1);
			Node childTri = child.get(3);
			childJedan.provjeri();
			childTri.provjeri();
			if (!Provjerinator.isCastable(childTri.getType(), childJedan.getType())){
				writeErrorMessage();
			}
			this.characteristics.setType(childJedan.getType());
			this.characteristics.setlIzraz(false);
			
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
