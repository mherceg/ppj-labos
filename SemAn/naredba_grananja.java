import java.util.List;

public class naredba_grananja extends Node {
	
	public naredba_grananja(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}
	/**
	 * Str 63f
	 */
	@Override
	public void provjeri() {
		int childCount = child.size();
		if(childCount == 5){
			Node childDva = child.get(2);
			Node childCetri = child.get(4);
			childDva.provjeri();
			// TODO 2. <izraz>.tip tilda int
			childCetri.provjeri();
		}
		else if(childCount == 7){
			Node childDva = child.get(2);
			Node childCetri = child.get(4);
			Node childSest = child.get(6);
			childDva.provjeri();
			// TODO 2. <izraz>.tip tilda int
			childCetri.provjeri();
			childSest.provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
