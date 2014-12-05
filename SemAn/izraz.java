import java.util.List;


public class izraz extends Node{
	
	public izraz(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * Str 61
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+izraz_pridruzivanja.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			childDva.provjeri();
			this.characteristics.setType(childDva.getType());
			this.characteristics.setlIzraz(false);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
