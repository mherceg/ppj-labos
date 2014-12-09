import java.awt.Window.Type;
import java.util.List;

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
			// TODO <ime_tipa>.tim != void .... childNula.getType != Tip.void
			this.characteristics.setType(childNula.getType());
			// TODO this.characteristics.setName(IDN.name);
		}
		else if(childCount == 4){
			childNula.provjeri();
			// TODO <ime_tipa>.tim != void .... childNula.getType != Tip.void
			// TODO this.characteristics.setType(niz(childNula.getType()));
			// TODO this.characteristics.setName(IDN.name); 
		}	
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}

	}

}
