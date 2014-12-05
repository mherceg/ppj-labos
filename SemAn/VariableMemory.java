import java.util.HashMap;

public class VariableMemory {

	private HashMap<String, Characteristics> hm;

	private VariableMemory previous;
	private VariableMemory next;

	private VariableMemory current;

	public VariableMemory() {
		this.hm = new HashMap<String, Characteristics>();
		this.current = this;
	}

	public void goDown() {
		this.next = new VariableMemory();
		this.next.previous = this;
		this.current = this.next;
	}

	public void goUp() {
		this.current = this.current.previous;
	}

	public boolean containsAtThisLevel(String name) {
		if (current.hm.containsKey(name)) {
			return true;
		}
		return false;
	}

	public boolean contains(String name) {
		VariableMemory iter = current;
		while (iter != null) {
			if (iter.containsAtThisLevel(name)) {
				return true;
			}
			iter = iter.previous;
			
		}
		return false;
	}

	public boolean add(String name, Characteristics value) {
		if (current.containsAtThisLevel(name)) {
			return false;
		}
		current.hm.put(name, value);
		return true;
	}

	public Characteristics get(String name) {
		Characteristics ret = null;
		VariableMemory iter = current;
		while (iter != null) {
			if ((ret = iter.hm.get(name)) != null) {
				return ret;
			}
			iter=iter.previous;
		}
		return null;

	}
}
