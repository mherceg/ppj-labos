import java.util.LinkedList;
import java.util.List;


public class Tip {
	private boolean array;
	private boolean function;
	private TipBasic glavni;
	private List<TipBasic> polje;
	
	
	public Tip(TipBasic glavni, List<TipBasic> polje, boolean array, boolean function) {
		this.setArray(array);
		this.setFunction(function);
		this.setGlavni(glavni);
		this.setPolje(polje);
	}
	public Tip(TipBasic glavni, boolean array){
		this(glavni, stvoriPolje(glavni), array, false);
	}
	
	private static List<TipBasic> stvoriPolje(TipBasic glavni2) {
		List<TipBasic> ret = new LinkedList<TipBasic>();
		if(glavni2!=null){
			ret.add(glavni2);
		}
		return ret;
	}
	public Tip(TipBasic glavni){
		this(glavni, null, false, false);
	}
	
	public Tip(){
		this(null, null, false, false);
	}
	
	public List<TipBasic> getPolje() {
		return polje;
	}
	
	public void setPolje(List<TipBasic> polje) {
		this.polje = new LinkedList<TipBasic>();
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
