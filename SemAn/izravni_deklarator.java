import java.util.List;


public class izravni_deklarator extends Node{
	
	public izravni_deklarator(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * 70
	 */
	@Override
	public void provjeri() {
		
		if(child.size()==1){
			//1.
			if(this.getType().equals(new Tip(TipBasic.VOID))){
				writeErrorMessage();
			}
			/*
			 * IDN.ime nije deklarirano u lokalnom djelokrugu
			 */
			/*
			 * zabiljezi deklaraciju IDN.ime s odgovaraju ́cim tipom
			 */
		}else{
			//TODO ima jos dosta
		}
	}

}
