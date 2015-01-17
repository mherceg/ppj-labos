import java.util.ArrayList;

public class primarni_izraz extends Node {

	public primarni_izraz(String name, boolean lIzraz, Tip type, int red,
			int brElem) {
		super(name, lIzraz, type, red, brElem);
	}

	/**
	 * Strana 51 gotov
	 */
	@Override
	public void provjeri() {
		if (child.size() == 1) {
			Node childNula = child.get(0);
			String value = childNula.getValue();
			if (childNula.getName().equals("IDN")) {
				if (!mem.contains(value) && !funcmem.contains(value)) {
					writeErrorMessage();
				}

				this.setValue(value);
				
				VariableMemory<Tip> varMem = mem;
				FunctionMemory funcMem = funcmem;

				Tip foundType = findType(value, varMem, funcMem);

				this.setType(foundType);
				this.characteristics.setlIzraz(childNula.getCharacteristics()
						.islIzraz());
			} else if (childNula.getName().equals("BROJ")) {
				if (!Provjerinator.rasponInt(value)) {
					writeErrorMessage();
				}
				this.setType(new Tip(TipBasic.INT));
				this.characteristics.setlIzraz(false);
				
				if (Integer.parseInt(value) < 10000){
					GeneratorKoda.append("MOVE %D " + value + ",R1");
					GeneratorKoda.append("PUSH R1");
				}
				else {
					GeneratorKoda.append("MOVE 0,R0");
					String[] chunks = chunkIt(value);
					int length = chunks.length;
					for (int i = 0; i < length - 1; ++i){
						if (i < length - 2){
							GeneratorKoda.append("MOVE %D 100, R2");
						}
						else{
							GeneratorKoda.append("MOVE %D " + Integer.toString((int) Math.pow(10,chunks[i+1].length()))+ ",R2");
						}
							GeneratorKoda.append("MOVE %D " + chunks[i] + ",R1");
							GeneratorKoda.append("ADD R1, R0, R1");
							
							String labelaZaMULPetlju = GeneratorKoda.newLabel();
							String labelaZaIzlazIzMULPetlje = GeneratorKoda.newLabel();
							
							GeneratorKoda.append("MOVE 0,R3");
							GeneratorKoda.append("OR R2,R2,R2");
							GeneratorKoda.append("JR_Z " + labelaZaIzlazIzMULPetlje);
							GeneratorKoda.append(labelaZaMULPetlju, "ADD R3,R1,R3");
							GeneratorKoda.append("SUB R2,1,R2");
							GeneratorKoda.append("JR_NZ " + labelaZaMULPetlju);
							GeneratorKoda.append(labelaZaIzlazIzMULPetlje, "MOVE R3, R0");
					}
					GeneratorKoda.append("MOVE %D " + chunks[length-1] + ",R3");
					GeneratorKoda.append("ADD R0, R3, R0");
					GeneratorKoda.append("PUSH R0");
				}
			} else if (childNula.getName().equals("ZNAK")) {
				if (!Provjerinator.znakOK(value)) {
					writeErrorMessage();
				}
				int broj = value.charAt(1);
				GeneratorKoda.append("MOVE %D " + broj + ",R1");
				GeneratorKoda.append("PUSH R1");
				
				this.setType(new Tip(TipBasic.CHAR));
				this.characteristics.setlIzraz(false);

			} else if (childNula.getName().equals("NIZ_ZNAKOVA")) {
				if (!Provjerinator.konstNizZnakovaOK(value)) {
					writeErrorMessage();
				}
				this.setType(new Tip(TipBasic.const_CHAR, true));
				this.setValue(value);
				this.characteristics.setlIzraz(false);

			} else {
				System.err.println("Greska kod " + this.getClass().getName()
						+ " za -> " + child.toString());
			}
		} else if (child.size() == 3) {
			Node childJedan = child.get(1);
			childJedan.provjeri();
			this.characteristics.setType(childJedan.getType());
			this.characteristics.setlIzraz(childJedan.getlIzraz());
		} else {
			System.err.println("Greska kod " + this.getClass().getName()
					+ " za -> " + child.toString());
		}
	}

	private String[] chunkIt(String value) {
		ArrayList<String> chunks = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length(); ++i){
			if (i % 2 == 0 && i != 0){
				chunks.add(sb.toString());
				sb = new StringBuilder();
			}
			sb.append(value.charAt(i));
		}
		chunks.add(sb.toString());
		String[] ret = new String[chunks.size()];
		for (int i = 0; i < chunks.size(); ++i){
			ret[i] = chunks.get(i);
		}
		return ret;
	}

	private Tip findType(String value, VariableMemory<Tip> varMem,
			FunctionMemory funcMem) {
		boolean found = false;
		Tip ret = null;

		VariableMemory<Tip> memo = varMem.current;
		VariableMemory<Function> func = funcMem.current;

		while (!found && (memo != null || func != null)) {
			if (memo != null) {
				if (memo.hm.containsKey(value)) {
					ret = memo.get(value);
					found = true;
				}
				memo = memo.previous;
			}
			if (func != null) {
				if (func.hm.containsKey(value)) {
					ret = func.get(value).getTipFunkcije();
					found = true;
				}
				func = func.previous;
			}
		}

		return ret;

	}

}
