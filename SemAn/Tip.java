import java.util.LinkedList;
import java.util.List;

public class Tip {
	private boolean array;
	private boolean function;
	private TipBasic glavni;// ili povratni tip ili osnovni tip (int,..)
	private List<Tip> polje;// to su parametri ili polje tipova

	public Tip(TipBasic glavni, List<Tip> polje, boolean array, boolean function) {
		this.setArray(array);
		this.setFunction(function);
		this.setGlavni(glavni);
		this.setPolje(polje);
	}

	public Tip(TipBasic glavni, boolean array) {
		this(glavni, new LinkedList<Tip>(), array, false);
	}

	public Tip(TipBasic glavni) {
		this(glavni, null, false, false);
	}

	public Tip() {
		this(null, null, false, false);
	}

	public List<Tip> getPolje() {
		return polje;
	}

	public void setPolje(List<Tip> polje) {
		this.polje = new LinkedList<Tip>();
		this.polje.addAll(polje);
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public boolean isFunction() {
		return function;
	}

	public void setFunction(boolean function) {
		this.function = function;
	}

	public TipBasic getGlavni() {
		return glavni;
	}

	public void setGlavni(TipBasic glavni) {
		this.glavni = glavni;
	}

}
