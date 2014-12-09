import java.util.List;


public class odnosni_izraz extends Node{
	
	public odnosni_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+aditivni_izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		// TODO ovdje je linija na ulazu <odnosni_izraz> (OP_LT | OP_GT | OP_LTE | OP_GTE) <aditivni_izraz>
		// Dal ej zagreda jedan child ali neki iz te skupine?
		else if(childNula.getName().equals("<"+odnosni_izraz.class.getName()+">")){
			Node childDva = child.get(2);
			childNula.provjeri();
			// TODO 2. <odnosni_izraz>.tip  int
			childDva.provjeri();
			// TODO 4. <aditivni_izraz>.tip  int
			// TODO this.characteristics.setType(Tip.int);
			this.characteristics.setlIzraz(false);
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}	
}
