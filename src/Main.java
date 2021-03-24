import java.util.List;
import java.util.*;

public class Main {
	public static void main(String[] args) throws Exception {
        String text1 = " ans1 = 10 + 20; ans2 = 10 + 30; ";
        List<Token> tokens1 = new Lexer().init(text1).tokenize();
        for (Token token : tokens1) {
            System.out.println(token.toString());
        }
        // --> variable "ans1"
        // --> sign "="
        // --> digit "10"
        // --> sign "+"
        // --> digit "20"

        String text2 = "a = 3 + 4 * 5";
        List<Token> tokens2 = new Lexer().init(text2).tokenize();
        List<Token> blk1 = new Parser().init(tokens2).block();
        for (Token ast : blk1) {
            System.out.println(ast.paren());
        }
        // --> (a = (3 + (4 * 5)))
        
        
        String text = "a = 3 + 4 * 5";
        List<Token> tokens = new Lexer().init(text).tokenize();
        List<Token> blk2 = new Parser().init(tokens).block();
        Map<String, Integer> variables = new Interpreter().init(blk2).run();
        for (Map.Entry<String, Integer> variable : variables.entrySet()) {
            System.out.println(variable.getKey() + " = " + variable.getValue());
        // --> a 23
        }
    }
}
