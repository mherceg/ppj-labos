import java.util.ArrayList;
import java.util.List;

public abstract class Node {
	private Characteristics characteristics;

	private List<Node> child = new ArrayList<Node>();

	public Node(String name, boolean lIzraz, Tip[] type, int red) {
		this.characteristics = new Characteristics(name, lIzraz, type, red);
	}

	public Characteristics getCharacteristics() {
		return characteristics;
	}

	public void addChild(Node newChild) {
		child.add(newChild);
	}

	public abstract Characteristics provjeri();

}
