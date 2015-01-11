
public class lista_naredbi extends Node {

	public lista_naredbi(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 62.
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+naredba.class.getName()+">")){
			childNula.provjeri();
		}
		else if(childNula.getName().equals("<"+lista_naredbi.class.getName()+">")){
			Node childJedan = child.get(1);
			childNula.provjeri();
			childJedan.provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
		GeneratorKoda.append("POP R0");
	}

}
