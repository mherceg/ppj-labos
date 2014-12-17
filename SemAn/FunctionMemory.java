
public class FunctionMemory extends VariableMemory<Function> {

	public FunctionMemory() {
		super();
	}

	@Override
	public boolean add(String name, Function function) {
		if (current.containsAtThisLevel(name)) {
			if (current.get(name).getImplementirana() == false) {
				if (current.get(name).getTipFunkcije()
						.equals(function.getTipFunkcije())) {
					if (function.getImplementirana() == true) {
						current.hm.replace(name, function);
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		current.hm.put(name, function);
		return true;
	}
	
	public void goUp() {
		SemantickiAnalizator.ArhivaFunkcija.addAll(this.current.hm.values());
		this.current = this.current.previous;
	}

}
