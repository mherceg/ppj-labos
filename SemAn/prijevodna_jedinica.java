import java.util.List;

public class prijevodna_jedinica extends Node {

	public prijevodna_jedinica(String name, boolean lIzraz, List<Tip> type,
			int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 65
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+vanjska_deklaracija.class.getName()+">")){
			childNula.provjeri();
		}
		else if(childNula.getName().equals("<"+prijevodna_jedinica.class.getName()+">")){
			Node childJedan = child.get(1);
			childNula.provjeri();
			childJedan.provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}

	}

}
