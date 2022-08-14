package stone.ast;

import ch06.Environment;
import stone.StoneException;
import stone.Token;

public class Name extends ASTLeaf {
    public Name(Token t) {
        super(t);
    }

    public String name() {
        return token.getText();
    }

    @Override
    public Object eval(Environment env) {
        Object value = env.get(name());
        if (value == null) {
            throw new StoneException("Undefined name: " + name(), this);
        } else {
            return value;
        }
    }
}
