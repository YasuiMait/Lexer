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

        String text2 = "a = -1";    // <-- Update
        text2 += "printf(a)";
        List<Token> tokens3 = new Lexer().init(text2).tokenize();
        List<Token> blk3 = new Parser().init(tokens3).block();
        new Interpreter().init(blk3).run();


        String text3 = "";
        text3 += "v = 0";
        text3 += "void addV(num) {";
        text3 += "  v = v + num";
        text3 += "}";
        text3 += "addV(3)";
        text3 += "printf(v)";
        List<Token> tokens4 = new Lexer().init(text3).tokenize();
        List<Token> blk4 = new Parser().init(tokens4).block();
        new Interpreter().init(blk4).run();
        // --> 3
    }
}