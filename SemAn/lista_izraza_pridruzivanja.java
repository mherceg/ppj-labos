import java.util.List;


public class lista_izraza_pridruzivanja extends Node{
	
	public lista_izraza_pridruzivanja(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
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
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		
		if(childNula.getName().equals("<lista_izraza_pridruzivanja>")){
			Node childDva = child.get(2);
			childNula.provjeri();
			childDva.provjeri();
			List<Tip> newTypeList = childNula.getType();
			newTypeList.addAll(childDva.getType());
			this.characteristics.setType(newTypeList);
			this.characteristics.setBrElem(childNula.getBrElem() + 1);
		}
		
	}

}
