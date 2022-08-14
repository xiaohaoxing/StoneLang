package stone.ast;

import ch06.BasicEvaluator;
import ch06.Environment;

import java.util.List;

public class BlockStmnt extends ASTList {
    public BlockStmnt(List<ASTree> children) {
        super(children);
    }

    @Override
    public Object eval(Environment env) {
        Object result = 0;
        for (ASTree t : this) {
            if (!(t instanceof NullStmnt)) {
                result = t.eval(env);
            }
        }
        return result;
    }
}
