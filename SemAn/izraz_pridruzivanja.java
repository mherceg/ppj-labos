import java.util.List;

public class izraz_pridruzivanja extends Node {

	public izraz_pridruzivanja(String name, boolean lIzraz, List<Tip> type,
			int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * Str 61
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+log_ili_izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
		}
		else if(childNula.getName().equals("<"+postfiks_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			// TODO <postfiks_izraz>.l-izraz = 1
			childDva.provjeri();
			// TODO <izraz_pridruzivanja>.tip  <postfiks_izraz>.tip
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(false);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
