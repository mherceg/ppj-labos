import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


public class SemantickiAnalizator {
	int Snjeguljica;
	char Patuljak;

	public static void main(String[] args) throws IOException {
		Stack<Node> stack = new Stack<>();
		
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		
		String input = "";
		input = br.readLine();
		Node node = NodeFactory.create(input);
		stack.push(node);
		
		int old_indent = 0;
		while ((input = br.readLine()) != null){
			int initial_length = input.length();
			input = input.replaceFirst("^ *", "");
			int new_indent = initial_length - input.length();
			node = NodeFactory.create(input);
			if (new_indent == old_indent){
				stack.pop();
			}
			if (new_indent < old_indent){
				for (int i = new_indent; i < old_indent; ++i){
					stack.pop();
				}
			}
			stack.peek().addChild(node);
			stack.push(node);
			old_indent = new_indent;
			
		}
		
		while(stack.size() > 1) stack.pop();
		stack.pop().provjeri();
		
	}

}
