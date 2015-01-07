
public class lista_parametara extends Node {

	public lista_parametara(String name, boolean lIzraz, Tip type, int red, int brElem) {
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
			this.setTypes(childNula.getType());
			this.setNames(childNula.getName());
		}
		else if(childNula.getName().equals("<"+lista_parametara.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			childDva.provjeri();
			if(childNula.getNames().contains(childDva.getName())){
				writeErrorMessage();
			}
			this.setTypes(childNula.getTypes());
			this.getTypes().add(childDva.getType());
			
			this.setNames(childNula.getNames());
			this.getNames().add(childDva.getName());
			
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
