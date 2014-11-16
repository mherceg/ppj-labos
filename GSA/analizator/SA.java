import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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
		
		context = JAXBContext.newInstance(Sink.class);
		um = context.createUnmarshaller();
		List<String> sinkronizacijski = ((Sink) um.unmarshal(new File("Sink.xml"))).getLista();
		
		HashSet<String> sinkro = new HashSet<>();
		sinkro.addAll(sinkronizacijski);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		
		Stack<StackElem> stack = new Stack<>();
		
		stack.push(new StackElem(akcija.getPocetno()));
		
		String in = "";
		Boolean f = false;
		while (true) {
			if (!f){
				if (in != null && in.equals("┴")){ break;}
				in = reader.readLine();
				if (in == null){
					in = "┴";
				}
			}
			f = false;
			Akcija ak = akcija.getAkcija(stack.peek().getStanje(),in.split(" ")[0]);
			while (ak == null && sinkro.contains(in.split(" ")[0])) {
				stack.pop();
				stack.pop();
				ak = akcija.getAkcija(stack.peek().getStanje(),in.split(" ")[0]);
			}
			if (ak == null){
				continue;
			}
			else
			if (ak.getAkcija().equals(Tip.Pomakni)){
				stack.push(new StackElem(new Node(in)));
				stack.push(new StackElem(ak.getLeft()));
				f = false;
			}
			else
			if (ak.getAkcija().equals(Tip.Reduciraj)){
				f = true;
				Stack<Node> st = new Stack<>();
				if (ak.getRight() == null){
					ArrayList<String> r = new ArrayList<>();
					r.add("$");
					ak.setRight(r);
					st.push(new Node("$"));
				}
				else{	
					List<String> right = ak.getRight();
					for (String c : right){
						stack.pop();
						Node n = stack.pop().getNode();
						//if (n.getName().startsWith(c)){
							st.push(n);
						//}
						//else{
						//	throw new IllegalThreadStateException("Nemogu reducirati");
						//}
					}
				}
				Node n = new Node(ak.getLeft());
				List<Node> children = new ArrayList<>();
				while(!st.isEmpty()){
					children.add(st.pop());
				}
				n.setChildren(children);
				String stanje = novoStanje.getAkcija(stack.peek().getStanje(), ak.getLeft()).getLeft();
				stack.push(new StackElem(n));
				stack.push(new StackElem(stanje));
			}
			else
			if (ak.getAkcija() == Tip.Prihvati){
				stack.pop();
				break;
			}
		}
		
		System.out.println(stack.peek().getNode().toPrint(""));
		
	}

}
