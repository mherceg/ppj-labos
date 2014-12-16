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
		Iterator<Tip> tipIter = this.getTypes().iterator();
		Iterator<String> imeIter = this.getNames().iterator();

		while (tipIter.hasNext() && imeIter.hasNext()) {
			mem.add(imeIter.next(), tipIter.next());
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
		mem.goUp();
		
	}

}
