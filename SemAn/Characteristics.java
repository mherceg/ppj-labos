import java.util.ArrayList;
import java.util.List;

public class Characteristics {

	private String name;
	private boolean lIzraz;
	private List<Tip> type = new ArrayList<Tip>();
	private int red;
	private int brElem;

	public Characteristics(String name, boolean lIzraz, List<Tip> type, int red, int brElem) {
		this.name = name;
		this.lIzraz = lIzraz;
		this.type = type;
		this.red = red;
		this.brElem = brElem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean islIzraz() {
		return lIzraz;
	}

	public void setlIzraz(boolean lIzraz) {
		this.lIzraz = lIzraz;
	}

	public void addType(Tip tip) {
		this.type.add(tip);
	}

	public int getRed() {
		return red;
	}

	public List<Tip> getType() {
		return type;
	}

	public void setType(List<Tip> type) {
		this.type = type;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getBrElem() {
		return brElem;
	}

	public void setBrElem(int brElem) {
		this.brElem = brElem;
	}
	
	

}
