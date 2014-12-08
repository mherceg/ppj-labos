import java.util.List;


public class UniformniZnak extends Node {

	String value;
	public UniformniZnak(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {

		this.characteristics = new Characteristics(name, lIzraz, type, red, brElem);

	}
	
	
	
	@Override
	public void provjeri() {
		// TODO Auto-generated method stub

	}

}
