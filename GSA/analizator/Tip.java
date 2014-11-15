import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Tip")
public enum Tip {
	@XmlElement
	Pomakni,Reduciraj,Stavi,Prihvati;
}
