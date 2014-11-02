import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class SA {

	public static void main(String[] args) throws JAXBException, IOException {

		JAXBContext context = JAXBContext.newInstance(Tablica.class);
		Unmarshaller um = context.createUnmarshaller();
		Tablica akcija = (Tablica) um.unmarshal(new File("Akcija.xml"));

		context = JAXBContext.newInstance(Tablica.class);
		um = context.createUnmarshaller();
		Tablica novoStanje = (Tablica) um.unmarshal(new File("NovoStanje.xml"));

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		
		Stack<StackElem> stack = new Stack<>();
		
		stack.push(new StackElem(akcija.getPocetno()));
		
		String in;
		while ((in = reader.readLine()) != null) {
			Akcija ak = akcija.getAkcija(stack.peek().getStanje(),in.split(" ")[0]);
			if (ak.getAkcija().equals(Tip.Pomakni)){
				stack.push(new StackElem(new Node(in)));
				stack.push(new StackElem(ak.getLeft()));
			}
			if (ak.getAkcija().equals(Tip.Reduciraj)){
				Stack<Node> st = new Stack<>();
				List<String> right = ak.getRight();
				for (String c : right){
					stack.pop();
					Node n = stack.pop().getNode();
					if (c.equals(n.getName())){
						st.push(n);
					}
					else{
						throw new IllegalThreadStateException("Nemogu reducirati");
					}
				}
				Node n = new Node(in);
				List<Node> children = new ArrayList<>();
				while(!st.isEmpty()){
					children.add(st.pop());
				}
				n.setChildren(children);
				stack.push(new StackElem(n));
				stack.push(new StackElem(novoStanje.getAkcija(stack.peek().getStanje(),in.split(" ")[0]).getLeft()));
			}
		}
		
		System.out.println(stack.peek().getNode().toPrint(""));
		
	}

}
