public class Provjerinator {
	
	public static boolean rasponChar(String value){
		int charValue;
		try {
			charValue=Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}
		if(charValue<0|| charValue>255){
			return false;
		}
		return true;
	}
	
	public static boolean rasponInt(String value) {
		int intValue;
		try {
			intValue=Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}
		if(intValue<-2147483648 || intValue>2147483647){
			return false;
		}
		return true;
	}
	
	public static boolean tilda(Tip a, Tip b){
		switch (a) {
		case null: //TODO
			
			break;

		default:
			break;
		}
		
		
		return false;
		
	}
	
	
}
