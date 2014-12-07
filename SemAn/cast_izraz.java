import java.util.List;

public class cast_izraz extends Node {

	public cast_izraz(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Str 55.
	 */
	@Override
	public void provjeri() {
		if(child.size() == 1){
			Node childNula = child.get(0);
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(child.size() == 4){
			Node childJedan = child.get(1);
			Node childTri = child.get(3);
			childJedan.provjeri();
			this.characteristics.setType(childJedan.getType());
			this.characteristics.setlIzraz(false);
			// TODO 3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
