import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		String text = "";
        text += "void add3(a1, a2, a3) {";
        text += "  return a1 + a2 + a3";
        text += "}";
        text += "v = add3(1,2,3)";
        text += "printf(v)";
        List<Token> tokens = new Lexer().init(text).tokenize();
        List<Token> blk = new Parser().init(tokens).block();
        new Interpreter().init(blk).run();
        // --> 6
	}
}