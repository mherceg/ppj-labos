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
		funcmem.goDown();
		Iterator<Tip> tipIter = this.getTypes().iterator();
		Iterator<String> imeIter = this.getNames().iterator();


		int definedBefore = mem.countCurrentFunctionVariables();
		System.out.println(definedBefore);
		while (tipIter.hasNext() && imeIter.hasNext()) {
			/*
			 * Integer.toHexString((definedBefore+1)*4)
			 * ide se po 4 jer su okteti, +1 jer se moramo maknut od R5
			 * 
			 */
			String imeNext = imeIter.next();
			System.out.println(imeNext+"   " + "R5-"+(definedBefore+1)*4);
			mem.add(imeNext, tipIter.next(), "R5-"+(definedBefore+1)*4);
//			System.out.println("dodao parametar");
			definedBefore++;
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
