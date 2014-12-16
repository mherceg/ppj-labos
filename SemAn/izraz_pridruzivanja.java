
public class izraz_pridruzivanja extends Node {

	public izraz_pridruzivanja(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * Str 61
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<log_ili_izraz>")){
			childNula.provjeri();
			//TODO Check this value assignement
			this.setValue(childNula.getValue());
			this.getCharacteristics().setlIzraz(childNula.getlIzraz());
			this.setType(childNula.getType());
		}
		else if(childNula.getName().equals("<"+postfiks_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			//  <postfiks_izraz>.l-izraz = 1
			if (childNula.getlIzraz() != true){
				writeErrorMessage();
			}
			
			childDva.provjeri();
			//  <izraz_pridruzivanja>.tip tilda <postfiks_izraz>.tip
			if(!Provjerinator.tilda(childDva.getType(), childNula.getType())){
				writeErrorMessage();
			}
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(false);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
