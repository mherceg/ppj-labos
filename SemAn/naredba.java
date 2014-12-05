import java.util.List;

public class naredba extends Node {

	public naredba(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 63
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+slozena_naredba.class.getName()+">")){
			childNula.provjeri();
		}
		else if(childNula.getName().equals("<"+izraz_naredba.class.getName()+">")){
			childNula.provjeri();
		}	
		else if(childNula.getName().equals("<"+naredba_grananja.class.getName()+">")){
			childNula.provjeri();
		}	
		else if(childNula.getName().equals("<"+naredba_petlje.class.getName()+">")){
			childNula.provjeri();
		}	
		else if(childNula.getName().equals("<"+naredba_skoka.class.getName()+">")){
			childNula.provjeri();
		}	
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}

	}

}
