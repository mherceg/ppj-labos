
public class lista_init_deklaratora extends Node{
	
	public lista_init_deklaratora(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * 68
	 * gotov?
	 * nasljedno svojstvo ntip
	 */
	@Override
	public void provjeri() {		
		Node childNula = child.get(0);
		if(childNula.getName().equals("<init_deklarator>")){
			childNula.setType(this.getType());
			childNula.provjeri();
		}
		else if(childNula.getName().equals("<lista_init_deklaratora>")){
			childNula.setType(this.getType());
			childNula.provjeri();
			
			Node childDva = child.get(2);
			
			childDva.setType(this.getType());
			childDva.provjeri();
			
			
		}
		
	}

}
