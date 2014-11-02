import java.util.List;

public class Akcija {
	private Tip akcija;
	private String left; // stanje
	private List<String> right;

	public Tip getAkcija() {
		return akcija;
	}

	public void setAkcija(Tip akcija) {
		this.akcija = akcija;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public List<String> getRight() {
		return right;
	}

	public void setRight(List<String> right) {
		this.right = right;
	}

}
