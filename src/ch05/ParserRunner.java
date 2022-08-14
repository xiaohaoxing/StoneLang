package ch05;

import stone.*;
import stone.ast.ASTree;

public class ParserRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser parser = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree ast = parser.parse(l);
            System.out.println("=>" + ast.toString());
        }
    }
}
