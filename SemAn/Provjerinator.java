public class Provjerinator {
	/**
	 * pravila za raspon CHAR, str 40, Brojevni tipovi
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
	 * @param znak
	 * @return
	 */
	public static boolean znakOK(String znak) {
		//TODO
		return true;
	}

	/**
	 * Pravila za NIZ_ZNAKOVA, str 42, 4.3.2
	 * @param znak
	 * @return
	 */
	public static boolean konstNizZnakovaOK(String niz) {
		//TODO
		return true;
	}
	/**
	 * a se moze implicitno pretvorit u b, str41, Implicitne promjene tipa
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean tilda(Tip a, Tip b) {
		//TODO
		return false;

	}
	/**
	 * a se moze kastat u b, str 42, Eksplicitne promjene tipa
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isCastable(Tip a, Tip b){
		//TODO
		return false;
	}
}
