import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class SemantickiAnalizator {
	int Snjeguljica;
	char Patuljak;
	
	public static List<Function> ArhivaFunkcija;

	public static void main(String[] args) throws IOException {
		Stack<Node> stack = new Stack<>();
		
		ArhivaFunkcija = new ArrayList<>();
		
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		
		String input = "";
		input = br.readLine();
		Node node = NodeFactory.create(input);
		stack.push(node);
		
		int old_indent = 0;
		while ((input = br.readLine()) != null){
			if (input.isEmpty()) break;
			int initial_length = input.length();
			input = input.replaceFirst("^ *", "");
			int new_indent = initial_length - input.length();
			node = NodeFactory.create(input);
			if (new_indent == old_indent){
				stack.pop();
			}
			if (new_indent < old_indent){
				for (int i = new_indent; i < old_indent + 1; ++i){
					stack.pop();
				}
			}
			stack.peek().addChild(node);
			stack.push(node);
			old_indent = new_indent;
			
		}
		
		while(stack.size() > 1) stack.pop();
		stack.pop().provjeri();
		
		/*
		 * Ako postoji main
		 */
		if(Node.funcmem.contains("main")){
			/*
			 * Ako programu NE postoji funkcija imena main i tipa funkcija(void -> int) syso...
			 */
			if(!(Node.funcmem.get("main").getTipFunkcije().equals(new Tip(TipBasic.INT,null,false, true)))){
				System.out.println("main");
				System.exit(0);
			}			
		}
		/*
		 * Ako ne postoji syso...
		 */
		else {
			System.out.println("main");
			System.exit(0);
		}
		ArhivaFunkcija.addAll(Node.funcmem.hm.values());
		for (Function f : ArhivaFunkcija){
			if (!f.getImplementirana()){
				System.out.println("funkcija");
				System.exit(0);
			}
		}
		
		
		
		
		
	}

}
