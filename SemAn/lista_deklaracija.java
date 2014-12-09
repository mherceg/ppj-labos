

public class lista_deklaracija extends Node{
	
	public lista_deklaracija(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	
	/**
	 * str 68
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<deklaracija>")){
			childNula.provjeri();
		}
		else if(childNula.getName().equals("<lista_deklaracija>")){
			Node childJedan = child.get(1);
			childJedan.provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
