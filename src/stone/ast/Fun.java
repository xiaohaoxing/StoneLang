package stone.ast;

import ch06.Environment;
import ch07.Function;

import java.util.List;

public class Fun extends ASTList {
    public Fun(List<ASTree> children) {
        super(children);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    @Override
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }

    public Object eval(Environment env) {
        return new Function(parameters(), body(), env);
    }
}
