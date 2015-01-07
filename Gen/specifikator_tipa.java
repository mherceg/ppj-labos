
public class specifikator_tipa extends Node {

	public specifikator_tipa(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 56.
	 * gotov
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("KR_VOID")){
			this.setType(new Tip(TipBasic.VOID));
		}
		else if(childNula.getName().equals("KR_CHAR")){
			this.setType(new Tip(TipBasic.CHAR));
		}
		else if(childNula.getName().equals("KR_INT")){
			this.setType(new Tip(TipBasic.INT));
		}
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
