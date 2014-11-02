import java.util.List;

public class Node {

	private String name;
	private List<Node> children;
	
	Node(String name){
		this.name = name;
		this.children = null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public String toPrint(String spaces){
		StringBuilder sb = new StringBuilder();
		sb.append(spaces);
		sb.append(name);
		sb.append("\n");
		for (Node n : children){
			sb.append(n.toPrint(spaces + " ")).append("\n");
		}
		return sb.toString();
	}
	
}
