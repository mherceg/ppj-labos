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

		}
		else if(childNula.getName().equals("<"+lista_parametara.class.getName()+">")){

		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
