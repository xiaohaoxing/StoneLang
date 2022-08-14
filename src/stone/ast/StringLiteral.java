package stone.ast;

import ch06.Environment;
import stone.Token;

public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) {
        super(t);
    }

    public String value() {
        return token.getText();
    }

    @Override
    public Object eval(Environment env) {
        return value();
    }
}
