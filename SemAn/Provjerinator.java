public class Provjerinator {
	/**
	 * pravila za raspon CHAR, str 40, Brojevni tipovi
	 * 
	 * @param value
	 * @return
	 */

	public static boolean rasponChar(String value) {
		int charValue;
		try {
			charValue = Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}
		if (charValue < 0 || charValue > 255) {
			return false;
		}
		return true;
	}

	/**
	 * ravila za raspon INT, str 40, Brojevni tipovi
	 * 
	 * @param value
	 * @return
	 */
	public static boolean rasponInt(String value) {
		int intValue;
		try {
			intValue = Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}
		if (intValue < -2147483648 || intValue > 2147483647) {
			return false;
		}
		return true;
	}

	/**
	 * Pravila za ZNAK, str 42, 4.3.2
	 * 
	 * @param znak
	 * @return
	 */
	public static boolean znakOK(String znak) {
		if (znak == null) return false;
		if (znak.length() == 1 || znak.length() == 3 && znak.charAt(0) == '\'' && znak.charAt(2) == '\'') return true;
		if (znak.length() == 2 && !znak.startsWith("\\")) return false;
		if (new String("tn0'\"\\").contains("" + znak.charAt(1))) return true;
		else return false;
	}

	/**
	 * Pravila za NIZ_ZNAKOVA, str 42, 4.3.2
	 * 
	 * @param znak
	 * @return
	 */
	public static boolean konstNizZnakovaOK(String niz) {
		boolean isOK = true;
		for (int i = 0; i < niz.length() - 1; ++i){
			if (niz.charAt(i) == '\\'){
				isOK &= znakOK("\\" + niz.charAt(i+1));
				++i;
			}
			else{
				isOK &= znakOK("" + niz.charAt(i));
			}
		}
		return isOK && znakOK(""+niz.charAt(niz.length()-1));
	}

	/**
	 * a se moze implicitno pretvorit u b, str41, Implicitne promjene tipa
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean tilda(Tip a, Tip b) {
		/*
		 * TODO Provjeriti na primjerima, neznam sta sa T i X ako se pojave
		 */
		if (a.equals(b)) {// refleksivna
			return true;
		}

		if (a.isFunction() || b.isFunction()) {
			return false;// valjda, nigdje se ne spominju funkcije
		}
		if (!a.isArray() && !b.isArray()) {
			if ((a.getGlavni() == TipBasic.CHAR && b.getGlavni() == TipBasic.const_CHAR)
					|| (a.getGlavni() == TipBasic.INT && b.getGlavni() == TipBasic.const_INT)
					|| (a.getGlavni() == TipBasic.const_CHAR && b.getGlavni() == TipBasic.CHAR)
					|| (a.getGlavni() == TipBasic.const_INT && b.getGlavni() == TipBasic.INT)) {
				return true;
			}
			if (a.getGlavni() == TipBasic.CHAR && b.getGlavni() == TipBasic.INT) {
				return true;
			}
		} else if (a.isArray() && b.isArray()) {// oba su nizovi

			if (TipBasic.equals(a.getGlavni(), TipBasic.T)
					&& (TipBasic.equals(b.getGlavni(), TipBasic.const_T))) {
				return true;
			}
		}
		return false;

	}

	/**
	 * a se moze kastat u b, str 42, Eksplicitne promjene tipa
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isCastable(Tip a, Tip b) {
		if (Provjerinator.tilda(a, b)) {
			return true;
		}

		if (!a.isArray() && !b.isArray()) {
			if (a.getGlavni() == TipBasic.INT && b.getGlavni() == TipBasic.CHAR) {
				return true;
			}
		}
		return false;
	}
}
