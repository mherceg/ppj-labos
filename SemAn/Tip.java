import java.util.LinkedList;
import java.util.List;

public class Tip {
	private boolean array;
	private boolean function;
	private TipBasic glavni;
	private List<Tip> polje;

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
		if (polje != null) {
			this.polje.addAll(polje);
		}
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tip)) {
			return false;
		}
		Tip other = (Tip) obj;
		if (array != other.array || function != other.function) {
			return false;
		}
		if (!TipBasic.equals(glavni, other.glavni)) {
			return false;
		}
		/*
		 * do ovuda je potrebno za sve situacije koliko ja znam, ostatak je za
		 * svaki slucaj
		 */
		if (function && other.function) {
			if (!(this.polje.containsAll(other.getPolje()) && other.polje
					.containsAll(this.getPolje()))) {
				return false;
			}
		}
		return true;
	}
}
