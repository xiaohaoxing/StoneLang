package stone.ast;

import ch06.Environment;
import stone.StoneException;

import java.util.List;

public class NegativeExpr extends ASTList {
    public NegativeExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree operand() {
        return child(0);
    }

    @Override
    public String toString() {
        return "-" + operand();
    }

    @Override
    public Object eval(Environment env) {
        Object v = operand().eval(env);
        if (v instanceof Integer) {
            return new Integer(-(Integer) v).intValue();
        } else {
            throw new StoneException("Bad type for -", this);
        }
    }
}
