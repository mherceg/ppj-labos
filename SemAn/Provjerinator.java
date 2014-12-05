public class Provjerinator {
	
	public static boolean rasponInt(String value) {
		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
}
