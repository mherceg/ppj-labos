import java.util.List;

public class inicijalizator extends Node {

	public inicijalizator(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);

	}

	/**
	 * 71
	 */
	@Override
	public void provjeri() {
		Node childNula = child.get(0);
		if (childNula.getName().equals("<izraz_pridruzivanja>")) {
			childNula.provjeri();
			if (childNula.getValue() != null) {
				int length = childNula.getValue().length();
				this.characteristics.setBrElem(length + 1 - 2); // zbog \0 i ""
				List<Tip> types = this.getTypes();
				for (int i = length - 1; i >= 0; --i) {
					types.add(new Tip(TipBasic.CHAR));
					// GeneratorKoda.append("POP R0");
					// int temp=i*4;
					// GeneratorKoda.append(this.getValue(),"STORE R0,("+this.getValue()+"-"+i*4+")");
				}
				this.setType(childNula.getType());
				// this.characteristics.setBrElem(duljina niza znakova + 1);

			} else {
				this.characteristics.setType(childNula.getType());
			}
		} else if (childNula.getName().equals("L_VIT_ZAGRADA")) {
			Node childJedan = child.get(1);
			childJedan.provjeri();
			this.characteristics.setBrElem(childJedan.getBrElem());
			this.characteristics.setType(childJedan.getType());
			int length = childJedan.getBrElem();
			VariableMemory<Tip>.MemoryElement all = mem.getWithLocation(this
					.getValue());

			GeneratorKoda.append("MOVE " + all.getLocation() + ",R1");
			for (int i = length - 1; i >= 0; --i) {

				GeneratorKoda.append("POP R0");
				int temp = i * 4;
				if (mem.containsAtGlobalLevel(getValue())) {
					GeneratorKoda.append("STORE R0,(R1-" + i * 4 + ")");
				}else{
					GeneratorKoda.append("PUSH R5");
					GeneratorKoda.append("LOAD R5,("+all.getFunctionLevelLabel()+")");
					GeneratorKoda.append("SUB R5,"+i*4+",R5");
					GeneratorKoda.append("STORE R0,R5");
					GeneratorKoda.append("POP R5");
				}

			}

		}
	}

}
