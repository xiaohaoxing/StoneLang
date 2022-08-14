package stone.ast;

import ch06.Environment;
import stone.Token;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) {
        super(t);
    }

    public int value() {
        return token.getNumber();
    }

    @Override
    public Object eval(Environment env) {
        return value();
    }
}
