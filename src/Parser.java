
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    private Map<String, Integer> degrees;
    private List<String> factorKinds;
    private List<String> binaryKinds;
    private List<String> rightAssocs;
    private List<String> unaryOperators;
    private List<String> reserved;

    public Parser() {
        degrees = new HashMap<>();
        degrees.put("(", 80);
        degrees.put("*", 60);
        degrees.put("/", 60);
        degrees.put("+", 50);
        degrees.put("-", 50);
        degrees.put("=", 10);
        factorKinds = Arrays.asList(new String[] { "digit", "ident" });
        binaryKinds = Arrays.asList(new String[] { "sign" });
        rightAssocs = Arrays.asList(new String[] { "=" });
        unaryOperators = Arrays.asList(new String[] { "+", "-" });
        reserved = Arrays.asList(new String[] { "void" });
    }

    private List<Token> tokens;
    private int i;

    public Parser init(List<Token> tokens) {
        i = 0;
        this.tokens = new ArrayList<Token>(tokens);
        Token eob = new Token();
        eob.kind = "eob";
        eob.value = "(eob)";
        this.tokens.add(eob);
        return this;
    }

    private Token token() throws Exception {
        if (tokens.size() <= i) {
            throw new Exception("No more token");
        }
        return tokens.get(i);
    }

    private Token next() throws Exception {
        Token t = token();
        ++i;
        return t;
    }

    private Token back() throws Exception {		//途中
    	--i;
    	Token t = token();
    	return t;
    }

    private Token consume(String expectedValue) throws Exception {
        if (!expectedValue.equals(token().value)) {
            throw new Exception("Not expected value");
        }
        return next();
    }

    private Token lead(Token token) throws Exception {
        if (token.kind.equals("ident") && token.value.equals("void")) {
            return func(token);
        } else if (factorKinds.contains(token.kind)) {
            return token;
        } else if (unaryOperators.contains(token.value)) {
            token.kind = "unary";
            token.left = expression(70);
            return token;
        } else if (token.kind.equals("paren") && token.value.equals("(")) {
            Token expr = expression(0);
            consume(")");
            return expr;
        } else {
            throw new Exception("The token cannot place there.");
        }
    }

    private Token func(Token token) throws Exception {
        token.kind = "func";
        token.ident = ident();
        consume("(");
        token.params = new ArrayList<Token>();
        if (!token().value.equals(")")) {
            token.params.add(ident());
            while (!token().value.equals(")")) {
                consume(",");
                token.params.add(ident());
            }
        }
        consume(")");
        consume("{");
        token.block = block();
        consume("}");
        return token;
    }

    private Token ident() throws Exception {	//途中
          Token id = next();
        if (!id.kind.equals("ident")) {
            throw new Exception("Not an identical token.");
        }
        if (reserved.contains(id.value)) {
        	Token id2 = next();
        	System.out.println(id2 + "あ");
        	Token id3 = back();
        	System.out.println(id3 + "い");
        	Token id4 = back();
        	id4 = back();
        	System.out.println(id4 + "う");
        	Token id5 = next();
        	id5 = next();
        	System.out.println(id5 + "え");
        	System.out.println(id2.value.equals(")"));
        	if(!(id2.value.equals(")") && id4.value.equals("("))) {
        		throw new Exception("The token was reserved.");
        	}
        }
        return id;
    }

    private int degree(Token t) {
        if (degrees.containsKey(t.value)) {
            return degrees.get(t.value);
        } else {
            return 0;
        }
    }

    private Token bind(Token left, Token operator) throws Exception {
        if (binaryKinds.contains(operator.kind)) {
            operator.left = left;
            int leftDegree = degree(operator);
            if (rightAssocs.contains(operator.value)) {
                leftDegree -= 1;
            }
            operator.right = expression(leftDegree);
            return operator;
        } else if (operator.kind.equals("paren") && operator.value.equals("(")) {
            operator.left = left;
            operator.params = new ArrayList<Token>();
            if (!token().value.equals(")")) {
                operator.params.add(expression(0));
                while (!token().value.equals(")")) {
                    consume(",");
                    operator.params.add(expression(0));
                }
            }
            consume(")");
            return operator;
        } else {
            throw new Exception("The token cannot place there.");
        }
    }

    public Token expression(int leftDegree) throws Exception {
        Token left = lead(next());
        int rightDegree = degree(token());
        while (leftDegree < rightDegree) {
            Token operator = next();
            left = bind(left, operator);
            rightDegree = degree(token());
        }
        return left;
    }

    public List<Token> block() throws Exception {
        List<Token> blk = new ArrayList<Token>();
        while (!token().kind.equals("eob")) {
            blk.add(expression(0));
        }
        return blk;
    }


}