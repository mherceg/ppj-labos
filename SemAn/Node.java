import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Node {
	protected Characteristics characteristics;
	private List<Tip> types;
	private List<String> names;

	protected List<Node> child = new ArrayList<Node>();
	
	protected static VariableMemory<Tip> mem = new VariableMemory<Tip>();
	protected static FunctionMemory funcmem = new FunctionMemory();
	
	public Node(String name, boolean lIzraz, Tip type, int red, int brElem) {

		this.characteristics = new Characteristics(name, lIzraz, type, red,
				brElem);
		this.types = new LinkedList<Tip>();
		this.names = new LinkedList<String>();
	}

	public Characteristics getCharacteristics() {
		return characteristics;
	}

	public void addChild(Node newChild) {
		child.add(newChild);
	}

	public abstract void provjeri();

	public String getName() {
		return characteristics.getName();
	}

	public boolean getlIzraz() {
		return characteristics.islIzraz();
	}

	public Tip getType() {
		return this.characteristics.getType();
	}

	public void setType(Tip type) {
		this.characteristics.setType(type);
	}

	public int getRed() {
		return characteristics.getRed();
	}

	public int getBrElem() {
		return characteristics.getBrElem();
	}

	public List<Tip> getTypes() {
		return types;
	}

	public void setTypes(Tip tip) {
		this.types = new LinkedList<Tip>();
		this.types.add(tip);
	}

	public void setTypes(List<Tip> tipovi) {
		this.types = new LinkedList<Tip>();
		this.types.addAll(tipovi);
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(String ime) {
		this.names = new LinkedList<String>();
		this.names.add(ime);
	}

	public void setNames(List<String> imena) {
		this.names = new LinkedList<String>();
		this.names.addAll(imena);
	}
	
	
	public void writeErrorMessage(){
		//TODO
	}
}
