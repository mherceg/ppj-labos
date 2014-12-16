public enum TipBasic {
	INT, CHAR, T, // zamijena za int ili char
	const_INT, const_CHAR, const_T, X, // zamjena za sve do sad
	VOID;
	
	public static TipBasic stripConst(TipBasic a){
		switch (a){
		case const_INT: return INT;
		case const_CHAR: return CHAR;
		case const_T: return T;
		default: return a;
		}
	}
	
	public static TipBasic addConst(TipBasic a){
		switch (a){
		case INT: return const_INT;
		case CHAR: return const_CHAR;
		case T: return const_T;
		default: return a;
		}
	}

	public static boolean equals(TipBasic a, TipBasic b) {
		if (a == b) {
			return true;
		}

		if ((a == T && (b == INT || b == CHAR))
				|| (b == T && (a == INT || a == CHAR))) {
			return true;
		}

		if ((a == const_T && (b == const_INT || b == const_CHAR))
				|| (b == const_T && (a == const_INT || a == const_CHAR))) {
			return true;
		}
		if(a==X || b==X){ // mijenjaju sve znakove
			return true;
		}
		return false;

	}
}
