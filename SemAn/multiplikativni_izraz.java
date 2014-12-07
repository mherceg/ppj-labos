import java.util.List;


public class multiplikativni_izraz extends Node{

	public multiplikativni_izraz(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 57.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+cast_izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+multiplikativni_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			// TODO 2. <mutliplikativni_izraz>.tip  int
			childDva.provjeri();
			// TODO 4. <cast_izraz>.tip  int
			// TODO this.characteristics.setType(Tip.int);
			this.characteristics.setlIzraz(false);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}
	

}
