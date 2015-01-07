
public class lista_izraza_pridruzivanja extends Node{
	
	public lista_izraza_pridruzivanja(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * 72
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<izraz_pridruzivanja>")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setBrElem(1);
		}
		
		if(childNula.getName().equals("<lista_izraza_pridruzivanja>")){
			Node childDva = child.get(2);
			childNula.provjeri();
			childDva.provjeri();
			
			this.setTypes(childNula.getTypes());
			this.getTypes().add(childDva.getType());
			this.characteristics.setBrElem(childNula.getBrElem() + 1);
		}
		
	}

}
