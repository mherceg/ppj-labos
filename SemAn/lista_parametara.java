import java.util.ArrayList;
import java.util.List;

public class lista_parametara extends Node {

	public lista_parametara(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 67
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+deklaracija_parametra.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			//this.characteristics.setName(name)		// TODO ovde isto lista imena
		}
		else if(childNula.getName().equals("<"+lista_parametara.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			childDva.provjeri();
			// TODO <deklaracija_parametra>.ime ne postoji u <lista_parametara>.imena
			List<Tip> tipovi = new ArrayList<Tip>(childNula.getType());
			tipovi.addAll(childDva.getType());
			this.characteristics.setType(tipovi);
			//this.characteristics.setName(name)	tu treba listu imena ubacit TODO
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
