
public class bin_ili_izraz extends Node {

	public bin_ili_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 60
	 * gotov
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+bin_xili_izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+bin_ili_izraz.class.getName()+">")){
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
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
