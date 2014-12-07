import java.util.List;

public class primarni_izraz extends Node {

	public primarni_izraz(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Strana 51
	 */
	@Override
	public void provjeri() {
		if(child.size() == 1){
			Node childNula = child.get(0);
			if(childNula.getName().equals("IDN")){
				
			}
			else if(childNula.getName().equals("BROJ")){
				// TODO kasno mi je za desifrirat ovde kaj treba .. fuck it za danas 
			}
			else if(childNula.getName().equals("ZNAK")){
				
			}
			else if(childNula.getName().equals("NIZ_ZNAKOVA")){
				
			}
			else{
				System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
			}
		}
		else if(child.size() == 3){
			Node childJedan = child.get(1);
			childJedan.provjeri();
			this.characteristics.setType(childJedan.getType());
			this.characteristics.setlIzraz(childJedan.getlIzraz());
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
	}

}
