import java.util.ArrayList;
import java.util.List;

public class lista_argumenata extends Node {

	public lista_argumenata(String name, boolean lIzraz, Tip type, int red, int brElem) {
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
			this.setTypes(childNula.getType());
		}
		else if(childNula.getName().equals("<"+lista_argumenata.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			childDva.provjeri();
			this.setTypes(childNula.getTypes());
			this.getTypes().add(childDva.getType());
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
