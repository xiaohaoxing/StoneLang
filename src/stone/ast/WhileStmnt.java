package stone.ast;

import ch06.Environment;

import java.util.List;

public class WhileStmnt extends ASTList {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public WhileStmnt(List<ASTree> children) {
        super(children);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }

    @Override
    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }

    @Override
    public Object eval(Environment env) {
        Object result = 0;
        for (; ; ) {
            Object c = condition().eval(env);
            if (c instanceof Integer && ((Integer) c).intValue() == FALSE) {
                return result;
            } else {
                result = body().eval(env);
            }
        }
    }
}

