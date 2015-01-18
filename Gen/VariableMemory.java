import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	protected int functionLevel;
	protected int addedCountForArrays;

	protected boolean functionStart;
	protected boolean functionNextGoDown;

	protected Map<Integer, String> functionLevelLabels;

	public class MemoryElement {
		private V value;
		private String location;
		private String functionLevelLabel;

		public MemoryElement(V value, String location) {
			this(value, location, "");
		}
		public MemoryElement(V value, String location, String functionLevelLabel) {
			this.value = value;
			this.setFunctionLevelLabel(functionLevelLabel);
			this.setLocation(location);
		}

		/**
		 * @return the location
		 */
		public String getLocation() {
			return location;
		}

		/**
		 * @param location
		 *            the location to set
		 */
		public void setLocation(String location) {
			this.location = location;
		}
		/**
		 * @return the functionLevelLabel
		 */
		public String getFunctionLevelLabel() {
			return functionLevelLabel;
		}
		/**
		 * @param functionLevelLabel the functionLevelLabel to set
		 */
		public void setFunctionLevelLabel(String functionLevelLabel) {
			this.functionLevelLabel = functionLevelLabel;
		}

	}

	public VariableMemory() {
		this.hm = new HashMap<String, MemoryElement>();
		this.current = this;
		this.level = 0;
		this.functionStart = false;
		this.functionNextGoDown = false;
		this.addedCountForArrays = 0;
		this.functionLevel = 0;
		this.functionLevelLabels = new HashMap<>();
	}

	public int getLevel() {
		return level;
	}

	public void goDown() {
		this.current.next = new VariableMemory<>();
		this.current.next.previous = this.current;
		this.current = this.current.next;
		if (this.functionNextGoDown == true) {
			this.functionNextGoDown = false;
			this.current.functionStart = true;
			this.functionLevel++;
			if (!functionLevelLabels.containsKey(functionLevel)) {
				String label = GeneratorKoda.newLabel();
				GeneratorKoda.appendLater(label, "DW 0");
				functionLevelLabels.put(functionLevel, label);
			}
		}
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
		return this.contains(name, true);
	}

	public boolean isLevelGlobal() {
		return level == 0;
	}

	public boolean isLevelLocal() {
		return level != 0;
	}

	public boolean add(String name, V value) {
		return this.add(name, value, "");

	}

	public boolean add(String name, V value, String location) {
		return this.add(name, value, location, "");
	}
	public boolean add(String name, V value, String location, String functiolLabel) {
		if (current.containsAtThisLevel(name)) {
			return false;
		}
		current.hm.put(name, new MemoryElement(value, location, functiolLabel));
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
			if (iter.hm.get(name) != null) {
				if ((ret = iter.hm.get(name).value) != null) {
					return ret;
				}
			}
			iter = iter.previous;
		}
		return null;
	}

	public int countCurrentlevelVariables() {
		return current.hm.size();
	}

	public int countCurrentFunctionVariables() {
		int cnt = 0;
		VariableMemory<V> iter = current;
		while (iter != null) {
			cnt += iter.hm.size() + iter.addedCountForArrays;
			if (iter.functionStart == true) {
				// System.out.println("kraj funkcije");
				break;
			} else {
				// System.out.println("nije kraj funkcije");
			}
			iter = iter.previous;

		}
		return cnt;
	}

	public void reduceFunctionLevel(int amout) {
		this.functionLevel -= amout;
	}

	public void addArrayElementsToCount(int cnt) {
		current.addedCountForArrays += cnt;
	}

	public void setCurrentFunctionStart(boolean bul) {
		this.current.functionStart = bul;
		this.functionLevel++;
		if (!functionLevelLabels.containsKey(functionLevel)) {
			String label = GeneratorKoda.newLabel();
			GeneratorKoda.appendLater(label, "DW 0");
			functionLevelLabels.put(functionLevel, label);
		}
	}

	public void setFunctionStartOnNextGoDown(boolean bul) {
		this.functionNextGoDown = bul;
	}

}
