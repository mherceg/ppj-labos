import java.util.ArrayList;
import java.util.List;

public class lista_argumenata extends Node {

	public lista_argumenata(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * str 54.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+izraz_pridruzivanja.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
		}
		else if(childNula.getName().equals("<"+lista_argumenata.class.getName()+">")){
			Node childJedan = child.get(1);
			childNula.provjeri();
			childJedan.provjeri();
			List<Tip> tipovi = new ArrayList<Tip>(childNula.getType());
			tipovi.addAll(childJedan.getType());
			this.characteristics.setType(tipovi);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
