import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Sink")
public class Sink {
	ArrayList<String> lista;

	public ArrayList<String> getLista() {
		return lista;
	}

	@XmlElement(name="lista")
	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
	
	

}
