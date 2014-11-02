
public class StackElem {
	Node node;
	String stanje;
	
	StackElem(String stanje){
		this.node = null;
		this.stanje = stanje;
	}
	
	StackElem(Node node){
		this.stanje = null;
		this.node = node;
	}
	
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public String getStanje() {
		return stanje;
	}
	public void setStanje(String stanje) {
		this.stanje = stanje;
	}
	
	
}
