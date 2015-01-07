

public class izraz_naredba extends Node{

	public izraz_naredba(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * str 63
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
		}
		else if(childNula.getName().equals("TOCKAZAREZ")){
			//this.characteristics.setType(Tip.int)	
			this.setType(new Tip(TipBasic.INT));
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}		
	}

}
