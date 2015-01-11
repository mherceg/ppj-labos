import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class prijevodna_jedinica extends Node {

	public prijevodna_jedinica(String name, boolean lIzraz, Tip type, int red, int brElem) {
		super(name, lIzraz, type, red, brElem);

	}
	/**
	 * Str 65
	 * gotov
	 * @throws IOException 
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		GeneratorKoda.append("MOVE 40000, R7");
		GeneratorKoda.append("CALL main");
		GeneratorKoda.append("HALT");
		if(childNula.getName().equals("<"+vanjska_deklaracija.class.getName()+">")){
			childNula.provjeri();
		}
		else if(childNula.getName().equals("<"+prijevodna_jedinica.class.getName()+">")){
			Node childJedan = child.get(1);
			childNula.provjeri();
			childJedan.provjeri();
		}		
		else{
			System.err.println("Greska kod " + this.getClass().getName() + " za -> " + child.toString());
		}
		FileWriter fw;
		try {
			fw = new FileWriter(new File("a.frisc"));
			fw.write(GeneratorKoda.sb.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
