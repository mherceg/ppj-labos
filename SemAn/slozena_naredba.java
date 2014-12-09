
public class slozena_naredba extends Node{
	
	public slozena_naredba(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 62.
	 */
	@Override
	public void provjeri() {
		Node childJedan = child.get(1);
		if(childJedan.getName().equals("<"+lista_naredbi.class.getName()+">")){
			childJedan.provjeri();
		}
		else if(childJedan.getName().equals("<"+lista_deklaracija.class.getName()+">")){
			Node childDva = child.get(2);
			childJedan.provjeri();
			childDva.provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
