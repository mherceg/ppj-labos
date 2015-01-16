import java.util.Iterator;


public class slozena_naredba extends Node{
	
	public slozena_naredba(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 62.
	 */
	@Override
	public void provjeri() {
		mem.goDown();
		funcmem.goDown(); // TODO prati VariableMemory.contains
		Iterator<Tip> tipIter = this.getTypes().iterator();
		Iterator<String> imeIter = this.getNames().iterator();


		int definedBefore = mem.countCurrentlevelVariables();
		while (tipIter.hasNext() && imeIter.hasNext()) {
			mem.add(imeIter.next(), tipIter.next(), "R5-"+Integer.toHexString((definedBefore+1)*4));
//			System.out.println("dodao parametar");
		}
		Node childJedan = child.get(1);
		if(childJedan.getName().equals("<lista_naredbi>")){
			childJedan.provjeri();
		}
		else if(childJedan.getName().equals("<lista_deklaracija>")){
			Node childDva = child.get(2);
			childJedan.provjeri();
			childDva.provjeri();
		}		

		funcmem.goUp();
		mem.goUp();
		
	}

}
