import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Red")
public class Red {
	private HashMap<String, Akcija> a;

	public Red(){
		a = new HashMap<>();
	}
	
	public HashMap<String, Akcija> getA() {
		return a;
	}
	
	@XmlElement(name="a")
	public void setA(HashMap<String,Akcija> a){
		this.a = a;
	}
	
}
