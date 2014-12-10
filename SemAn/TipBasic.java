public enum TipBasic {
	INT, CHAR, T, // zamijena za int ili char
	const_INT, const_CHAR, const_T, X, // zamjena za sve do sad
	VOID;

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