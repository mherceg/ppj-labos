
public class jednakosni_izraz extends Node {

	public jednakosni_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * str 58.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+odnosni_izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+jednakosni_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			//  2. <jednakosni_izraz>.tip tilda int
			if(!Provjerinator.tilda(childNula.getType(), new Tip(TipBasic.INT))){
				writeErrorMessage();
			}
			childDva.provjeri();
			//  4. <odnosni_izraz>.tip tilda int
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
