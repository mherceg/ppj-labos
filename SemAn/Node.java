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
	
	//potrebno za return tip;
	protected static TipBasic activeFuncRetType;
	protected TipBasic getActiveFuncRetType() {
		return activeFuncRetType;
	}

	public void setActiveFuncRetType(TipBasic activeFuncRetType) {
		Node.activeFuncRetType = activeFuncRetType;
	}
	
	
	/* potrebno za break; i continue;
	 * kaze u kojoj dubini petlje smo
	 */
	protected static int loopdepth;
	
	public static void enterLoop(){
		++loopdepth;
	}
	public static void exitLoop(){
		--loopdepth;
	}
	public static boolean inLoop(){
		return loopdepth!=0;
	}
	
	protected String value;
	
	
	public Node(String name, boolean lIzraz, Tip type, int red, int brElem) {

		this.characteristics = new Characteristics(name, lIzraz, type, red,
				brElem);
		this.types = new LinkedList<Tip>();
		this.names = new LinkedList<String>();
		loopdepth = 0;
		activeFuncRetType=null;
	}

	public Characteristics getCharacteristics() {
		return characteristics;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
		StringBuilder sb = new StringBuilder();
		sb.append("<"+this.getClass().getName()+"> ::=");
		
		for (Node c : child){
			sb.append(" ");
			if (c.getClass() == UniformniZnak.class){
				sb.append(c.toString());
			}
			else{
				sb.append("<"+c.getClass().getName()+">");
			}
		}
		
		System.out.println(sb.toString());
		System.exit(0);
	}

	



	

	
}
