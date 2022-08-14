package ch06;

import stone.*;
import stone.ast.ASTree;
import stone.ast.NullStmnt;

public class BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new BasicParser(), new BasicEnv());
    }

    public static void run(BasicParser parser, Environment env) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree tree = parser.parse(lexer);
            if (!(tree instanceof NullStmnt)) {
                Object r = tree.eval(env);
                System.out.println("=>" + r);
            }
        }
    }
}
