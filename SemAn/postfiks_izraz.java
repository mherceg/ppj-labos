import java.util.List;

public class postfiks_izraz extends Node {

	public postfiks_izraz(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * Strana 52
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if(childNula.getName().equals("<"+primarni_izraz.class.getName()+">")){
			childNula.provjeri();
			this.characteristics.setType(childNula.getType());
			this.characteristics.setlIzraz(childNula.getlIzraz());
		}
		else if(childNula.getName().equals("<"+postfiks_izraz.class.getName()+">")){
			if(child.size() == 2){
				childNula.provjeri();
				// TODO 2. <postfiks_izraz>.l-izraz = 1 i <postfiks_izraz>.tip  int
				// TODO this.characteristics.setType(Tip.int);
				this.characteristics.setlIzraz(false);
			}
			else if(child.size() == 3){
				childNula.provjeri();
				// TODO 2. <postfiks_izraz>.tip = funkcija(void ! pov)
				// TODO this.characteristics.setType(Tip.pov);
				this.characteristics.setlIzraz(false);
			}
			else if(child.size() == 4){
				if(childNula.getName().equals("<"+izraz.class.getName()+">")){
					Node childDva = child.get(2);
					childNula.provjeri();
					// TODO 2. <postfiks_izraz>.tip = niz (X)
					childDva.provjeri();
					// TODO 4. <izraz>.tip  int
					// TODO this.characteristics.setType(Tip.X);
					// TODO this.characteristics.setlIzraz( l-izraz  X != const(T));
				}
				else if(childNula.getName().equals("<"+lista_argumenata.class.getName()+">")){
					Node childDva = child.get(2);
					childNula.provjeri();
					childDva.provjeri();
					// TODO 3. <postfiks_izraz>.tip = funkcija(params ! pov) i redom po elementima
					// arg-tip iz <lista_argumenata>.tipovi i param-tip iz params vrijedi arg-tip
					//  param-tip
					// TODO this.characteristics.setType(Tip.pov);
					this.characteristics.setlIzraz(false);
				}
				else{
					System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
				}
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
