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


        String text = "";
        text += "v = 0";
        text += "void add3(a1, a2, a3) {";
        text += "  v = a1 + a2 + a3";
        text += "}";
        text += "add3(1,2,3)";
        text += "printf(v)";
        List<Token> tokens = new Lexer().init(text).tokenize();
        List<Token> blk = new Parser().init(tokens).block();
        new Interpreter().init(blk).run();
        // --> 6
        
        String T = "";
        T += "v = 0";
        T += "void add3(void) {";
        T += "v = 1";
        T += "}";
        T += "add3()";
        T += "printf(v)";
        List<Token> tokens5 = new Lexer().init(T).tokenize();
        List<Token> blk5 = new Parser().init(tokens5).block();
        new Interpreter().init(blk5).run();
        // --> 1
    }
}