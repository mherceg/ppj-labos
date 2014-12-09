import java.util.List;


public class inicijalizator extends Node{
	
	public inicijalizator(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * 71
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<izraz_pridruzivanja>")){
			childNula.provjeri();
			if(false/*childNula => NIZ_ZNAKOVA*/){	// TODO
				//this.characteristics.setBrElem(duljina niza znakova + 1);
				//this.characteristics.addType(tip);
			}
			else{
				this.characteristics.setType(childNula.getType());
			}
		}
		else if(childNula.getName().equals("L_VIT_ZAGRADA")){	//  TODO provjerit dal se ovo ovak radi
			Node childJedan = child.get(1);
			childJedan.provjeri();
			this.characteristics.setBrElem(childJedan.getBrElem());
			this.characteristics.setType(childJedan.getType());
		}	
	}
	
	

}
