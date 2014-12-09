import java.util.ArrayList;
import java.util.List;

public class TESTZAMEMORIJUFUNKCIJA {

	public static void main(String[] args) {
		VariableMemory<Function> memorija = new VariableMemory<Function>();
		List<Tip> listTipova = new ArrayList<Tip>();
		listTipova.add(new Tip(TipBasic.CHAR));
		listTipova.add(new Tip(TipBasic.INT));
		memorija.add("dodaj", new Function(new Tip(TipBasic.INT, listTipova,
				false, true), false));
		memorija.add("dodaj", new Function(new Tip(TipBasic.INT, listTipova,
				false, true), true));
		memorija.add("ghdahadh", new Function(new Tip(TipBasic.INT, listTipova,
				false, true), false));
		memorija.add("kngea", new Function(new Tip(TipBasic.INT, listTipova,
				false, true), false));
		memorija.add("dga", new Function(new Tip(TipBasic.INT, listTipova,
				false, true), true));

	}

}
