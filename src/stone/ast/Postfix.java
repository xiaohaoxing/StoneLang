package stone.ast;

import ch06.Environment;

import java.util.List;

public abstract class Postfix extends ASTList {
    public Postfix(List<ASTree> children) {
        super(children);
    }

    public abstract Object eval(Environment env, Object value);
}
