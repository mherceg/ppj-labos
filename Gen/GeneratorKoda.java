import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;


public class GeneratorKoda {
	int Snjeguljica;
	char Patuljak;
	
	public static StringBuilder sb = new StringBuilder();
	public static StringBuilder init = new StringBuilder();
	public static StringBuilder later = new StringBuilder();
	public static Set<String> Labels = new HashSet<>();
	
	public static List<Function> ArhivaFunkcija;

	public static void main(String[] args) throws IOException {
		Stack<Node> stack = new Stack<>();
		
		ArhivaFunkcija = new ArrayList<>();
		
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		
		sb.append("MOVE 40000, R7" + '\n');
		sb.append("CALL init" + '\n');
		sb.append("CALL main" + '\n');
		sb.append("HALT" + '\n');
		
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
		
		//newLabel();
		FileWriter fw;
		try {
			fw = new FileWriter(new File("a.frisc"));
			fw.write(GeneratorKoda.sb.toString() + '\n');
			appendInit("RET");
			fw.write("init " + GeneratorKoda.init.toString() + '\n');
			fw.write(GeneratorKoda.later.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(-1); //ignoriramo arhivu funkcija
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
//		ArhivaFunkcija.addAll(Node.funcmem.hm.values());
		for (Function f : ArhivaFunkcija){
			if (!f.getImplementirana()){
				System.out.println("funkcija");
				System.exit(0);
			}
		}
		
	}
	
	public static void append(String smth){
		if (!definicija_funkcije.inFunction){
			appendInit(smth);
		}
		else{
			sb.append("    " + smth + "\n");
		}
	}
	public static void append(String label, String smth){
		if (!definicija_funkcije.inFunction){
			appendInit(label,smth);
		}
		else{
			sb.append(label + " " + smth + "\n");
		}
	}
	public static void appendBez(String smth){
		if (!definicija_funkcije.inFunction){
			appendBezInit(smth);
		}
		else{
			sb.append(smth);
		}
	}
	
	public static void appendInit(String smth){
		init.append("    " + smth + "\n");
	}
	public static void appendInit(String label, String smth){
		init.append(label + " " + smth + "\n");
	}
	public static void appendBezInit(String smth){
		init.append(smth);
	}
	
	public static void appendLater(String smth){
		later.append("    " + smth + "\n");
	}
	public static void appendLater(String label, String smth){
		later.append(label + " " + smth + "\n");
	}
	public static void appendBezLater(String smth){
		later.append(smth);
	}

	public static String newLabel(){
		StringBuilder lb = new StringBuilder();
		for (int i = 0; i < 6; ++i){
			char rnd = (char) ('a' + (char) new Random().nextInt('z'-'a'));
			lb.append(rnd);
		}
		if (!Labels.contains(lb.toString())){
			return lb.toString();
		}
		else{
			return newLabel();
		}
	}
}
