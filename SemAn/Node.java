import java.util.ArrayList;
import java.util.List;

public abstract class Node {
	protected Characteristics characteristics;

	protected List<Node> child = new ArrayList<Node>();

	public Node(String name, boolean lIzraz, List<Tip> type, int red) {

		this.characteristics = new Characteristics(name, lIzraz, type, red);

	}

	public Node() {
		super();
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

	public Tip getFirstType() {
		return this.characteristics.getType().get(0);
	}

	public List<Tip> getType() {
		return this.characteristics.getType();
	}

	public void setType(List<Tip> type) {
		this.characteristics.setType(type);
	}

	public int getRed() {
		return characteristics.getRed();
	}

}
