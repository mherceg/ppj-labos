
public class Characteristics {

	private String name;
	private boolean lIzraz;
	private Tip type;
	private int red;
	private int brElem;

	public Characteristics(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		this.name = name;
		this.lIzraz = lIzraz;
		if(type == null) type = new Tip();
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

	public void addType(TipBasic tip) {
		this.type.getPolje().add(tip);
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public Tip getType() {
		return type;
	}

	public void setType(Tip type) {
		this.type = type;
	}

	public int getBrElem() {
		return brElem;
	}

	public void setBrElem(int brElem) {
		this.brElem = brElem;
	}

}
