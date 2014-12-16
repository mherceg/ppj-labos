
public class NodeFactory {
	static Node create(String input){
		if (input.startsWith("<")){
			input.replaceAll(" ", "");
			switch(input){
			case "<primarni_izraz>":
				return new primarni_izraz(input, true, null, 0, 0);
			case "<postfiks_izraz>":
				return new postfiks_izraz(input, true, null, 0, 0);
			case "<lista_argumenata>":
				return new lista_argumenata(input, true, null, 0, 0);
			case "<unarni_izraz>":
				return new unarni_izraz(input, true, null, 0, 0);
			case "<unarni_operator>":
				return new unarni_operator(input, true, null, 0, 0);
			case "<cast_izraz>":
				return new cast_izraz(input, true, null, 0, 0);
			case "<ime_tipa>":
				return new ime_tipa(input, true, null, 0, 0);
			case "<specifikator_tipa>":
				return new specifikator_tipa(input, true, null, 0, 0);
			case "<multiplikativni_izraz>":
				return new multiplikativni_izraz(input, true, null, 0, 0);
			case "<aditivni_izraz>":
				return new aditivni_izraz(input, true, null, 0, 0);
			case "<odnosni_izraz>":
				return new odnosni_izraz(input, true, null, 0, 0);
			case "<jednakosni_izraz>":
				return new jednakosni_izraz(input, true, null, 0, 0);
			case "<bin_i_izraz>":
				return new bin_i_izraz(input, true, null, 0, 0);
			case "<bin_xili_izraz>":
				return new bin_xili_izraz(input, true, null, 0, 0);
			case "<bin_ili_izraz>":
				return new bin_ili_izraz(input, true, null, 0, 0);
			case "<log_i_izraz>":
				return new log_i_izraz(input, true, null, 0, 0);
			case "<log_ili_izraz>":
				return new log_ili_izraz(input, true, null, 0, 0);
			case "<izraz_pridruzivanja>":
				return new izraz_pridruzivanja(input, true, null, 0, 0);
			case "<izraz>":
				return new izraz(input, true, null, 0, 0);
			case "<slozena_naredba>":
				return new slozena_naredba(input, true, null, 0, 0);
			case "<lista_naredbi>":
				return new lista_naredbi(input, true, null, 0, 0);
			case "<naredba>":
				return new naredba(input, true, null, 0, 0);
			case "<izraz_naredba>":
				return new izraz_naredba(input, true, null, 0, 0);
			case "<naredba_grananja>":
				return new naredba_grananja(input, true, null, 0, 0);
			case "<naredba_petlje>":
				return new naredba_petlje(input, true, null, 0, 0);
			case "<naredba_skoka>":
				return new naredba_skoka(input, true, null, 0, 0);
			case "<prijevodna_jedinica>":
				return new prijevodna_jedinica(input, true, null, 0, 0);
			case "<vanjska_deklaracija>":
				return new vanjska_deklaracija(input, true, null, 0, 0);
			case "<definicija_funkcije>":
				return new definicija_funkcije(input, true, null, 0, 0);
			case "<lista_parametara>":
				return new lista_parametara(input, true, null, 0, 0);
			case "<deklaracija_parametra>":
				return new deklaracija_parametra(input, true, null, 0, 0);
			case "<lista_deklaracija>":
				return new lista_deklaracija(input, true, null, 0, 0);
			case "<deklaracija>":
				return new deklaracija(input, true, null, 0, 0);
			case "<lista_init_deklaratora>":
				return new lista_init_deklaratora(input, true, null, 0, 0);
			case "<init_deklarator>":
				return new init_deklarator(input, true, null, 0, 0);
			case "<izravni_deklarator>":
				return new izravni_deklarator(input, true, null, 0, 0);
			case "<inicijalizator>":
				return new inicijalizator(input, true, null, 0, 0);
			case "<lista_izraza_pridruzivanja>":
				return new lista_izraza_pridruzivanja(input, true, null, 0, 0);
			default:
				throw new IllegalArgumentException("Invalid node name");
			}
		}
		else{
			String[] parts = input.split(" ");
			Boolean lIzraz = parts[0].equals("IDN");
			if (parts[0].equals("ZNAK")){ parts[2] = parts[2].replaceAll("'", "");}
			if (parts[0].equals("NIZ_ZNAKOVA")){ parts[2] = parts[2].replaceAll("\"", "");}
			return new UniformniZnak(parts[0], lIzraz, null, Integer.parseInt(parts[1]), 0, parts[2]);
		}
	}
}
