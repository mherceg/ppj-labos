public class Characteristics {

	private String name;
	private boolean lIzraz;
	private Tip[] type;
	private int red;

	public Characteristics(String name, boolean lIzraz, Tip[] type, int red) {
		super();
		this.name = name;
		this.lIzraz = lIzraz;
		this.type = type;
		this.red = red;
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

	public Tip[] getType() {
		return type;
	}

	public void setType(Tip[] type) {
		this.type = type;
	}

	public int getRed() {
		return red;
	}

}
