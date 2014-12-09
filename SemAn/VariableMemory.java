import java.util.HashMap;
/**
 * @author Adriano Baćac
 *
 *
 * Memorija za varijable.
 * String => V
 *
 * @param <V> Tip podataka
 */
public class VariableMemory<V> {

	private HashMap<String, V> hm;

	private VariableMemory<V> previous;
	private VariableMemory<V> next;

	private VariableMemory<V> current;

	public VariableMemory() {
		this.hm = new HashMap<String, V>();
		this.current = this;
	}

	public void goDown() {
		this.next = new VariableMemory<>();
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
		VariableMemory<V> iter = current;
		while (iter != null) {
			if (iter.containsAtThisLevel(name)) {
				return true;
			}
			iter = iter.previous;
			
		}
		return false;
	}

	public boolean add(String name, V value) {
		if (current.containsAtThisLevel(name)) {
			return false;
		}
		current.hm.put(name, value);
		return true;
	}

	public V get(String name) {
		V ret = null;
		VariableMemory<V> iter = current;
		while (iter != null) {
			if ((ret = iter.hm.get(name)) != null) {
				return ret;
			}
			iter=iter.previous;
		}
		return null;

	}
}
