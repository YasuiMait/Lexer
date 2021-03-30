import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		String text1 = "a = (3 + 4) * 5";
        List<Token> tokens1 = new Lexer().init(text1).tokenize();
        List<Token> blk1 = new Parser().init(tokens1).block();
        for (Token ast : blk1) {
            System.out.println(ast.paren());
        }
        // --> (a = (3 + (4 * 5)))
        System.out.println( new Interpreter().init(blk1).run() );

        text1 += "printf(a)";
        List<Token> tokens2 = new Lexer().init(text1).tokenize();
        List<Token> blk2 = new Parser().init(tokens2).block();
        new Interpreter().init(blk2).run();
        // --> 35
        //a
    }
}
