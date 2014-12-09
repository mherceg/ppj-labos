import java.util.List;


public class naredba_petlje extends Node{
	
	public naredba_petlje(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}
	/**
	 * Str 64
	 */
	@Override
	public void provjeri() {
		Node childDva = child.get(2);
		if(childDva.getName().equals("<"+izraz.class.getName()+">")){
			Node childCetiri = child.get(4);
			childDva.provjeri();
			// TODO 2. <izraz>.tip  int
			childCetiri.provjeri();
		}
		else if(childDva.getName().equals("<"+izraz_naredba.class.getName()+">")){
			int childCount = child.size();
			if(childCount == 6){
				Node childTri = child.get(3);
				Node childPet = child.get(5);
				childDva.provjeri();
				childTri.provjeri();
				// TODO 3. <izraz_naredba>2.tip tilda int
				childPet.provjeri();
			}
			else if(childCount == 7){
				Node childTri = child.get(3);
				Node childCetri = child.get(4);
				Node childSedam = child.get(7);
				childDva.provjeri();
				childTri.provjeri();
				// TODO <izraz_naredba>2.tip tilda int
				childCetri.provjeri();
				childSedam.provjeri();
			}
			else{
				System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
			}
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
		
	}

}
