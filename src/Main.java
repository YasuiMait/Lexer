import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
        String text = " ans1 = 10 + 20 ";
        List<Token> tokens = new Lexer().init(text).tokenize();
        for (Token token : tokens) {
            System.out.println(token.toString());
        }
        // --> variable "ans1"
        // --> sign "="
        // --> digit "10"
        // --> sign "+"
        // --> digit "20"
    }

}
