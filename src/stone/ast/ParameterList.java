package stone.ast;

import ch06.Environment;

import java.util.List;

public class ParameterList extends ASTList {
    public ParameterList(List<ASTree> children) {
        super(children);
    }

    public String name(int i) {
        return ((ASTLeaf) child(i)).token.getText();
    }

    public int size() {
        return children.size();
    }

    public void eval(Environment env, int index, Object value) {
        env.putNew(name(index), value);
    }
}
