import java.util.HashMap;

/**
 * @author Adriano Bacac
 *
 *
 *         Memorija za varijable. String => V
 *
 * 
 * @param <V>
 *            Tip podataka, predlazem Characteristics
 */
public class VariableMemory<V> {

	protected HashMap<String, MemoryElement> hm;

	protected VariableMemory<V> previous;
	protected VariableMemory<V> next;

	protected VariableMemory<V> current;

	protected int level;

	public class MemoryElement {
		private V value;
		private String location;

		public MemoryElement(V value, String location) {
			super();
			this.value = value;
			this.location = location;
		}

	}

	public VariableMemory() {
		this.hm = new HashMap<String, MemoryElement>();
		this.current = this;
		this.level = 0;
	}

	public int getLevel() {
		return level;
	}

	public void goDown() {
		this.current.next = new VariableMemory<>();
		this.current.next.previous = this.current;
		this.current = this.current.next;
		level++;
	}

	public void goUp() {
		this.current = this.current.previous;
		level--;
	}

	public boolean containsAtThisLevel(String name) {

		if (current.hm.containsKey(name)) {
			return true;
		}
		return false;
	}

	public boolean containsAtGlobalLevel(String name) {

		if (this.hm.containsKey(name)) {
			return true;
		}
		return false;
	}

	public boolean contains(String name, boolean searchGlobal) {
		VariableMemory<V> iter = current;
		VariableMemory<V> limit = null;
		if (!searchGlobal) {
			limit = this;
		}
		while (iter != limit) {
			/*
			 * lokalna razina se tretira kao nova globalna razina
			 */
			if (iter.containsAtGlobalLevel(name)) {
				return true;
			}
			iter = iter.previous;

		}
		return false;
	}

	public boolean contains(String name) {
		return this.contains(name, false);
	}

	public boolean isLevelGlobal() {
		return level != 0;
	}

	public boolean isLevelLocal() {
		return level == 0;
	}

	public boolean add(String name, V value, String location) {
		if (current.containsAtThisLevel(name)) {
			return false;
		}
		current.hm.put(name, new MemoryElement(value, location));
		return true;
	}
	
	public MemoryElement getWithLocation(String name) {
		MemoryElement ret = null;
		VariableMemory<V> iter = current;
		while (iter != null) {
			if ((ret = iter.hm.get(name)) != null) {
				return ret;
			}
			iter = iter.previous;
		}
		return null;
	}
	
	public V get(String name) {
		V ret = null;
		VariableMemory<V> iter = current;
		while (iter != null) {
			if ((ret = iter.hm.get(name).value) != null) {
				return ret;
			}
			iter = iter.previous;
		}
		return null;
	}

	public boolean add(String name, V value) {
		return this.add(name, value, "");

	}
}
