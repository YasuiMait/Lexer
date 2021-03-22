import java.util.List;

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
        List<Token> blk = new Parser().init(tokens2).block();
        for (Token ast : blk) {
            System.out.println(ast.paren());
        }
        // --> (a = (3 + (4 * 5)))
    }
}
