import java.util.List;

public class specifikator_tipa extends Node {

	public specifikator_tipa(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 56.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("KR_VOID")){
			//this.characteristics.setType(Tip.void);	TODO
		}
		else if(childNula.getName().equals("KR_CHAR")){
			//this.characteristics.setType(Tip.char);
		}
		else if(childNula.getName().equals("KR_INT")){
			//this.characteristics.setType(Tip.int);
		}
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
