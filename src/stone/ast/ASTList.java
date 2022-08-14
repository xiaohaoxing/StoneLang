package stone.ast;

import ch06.Environment;
import stone.StoneException;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
    protected List<ASTree> children;

    public ASTList(List<ASTree> children) {
        this.children = children;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    @Override
    public String location() {
        for (ASTree t : children) {
            String s = t.location();
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Iterator<ASTree> iterator() {
        return super.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        String sep = "";
        for (ASTree t : children) {
            sb.append(sep);
            sep = " ";
            sb.append(t.toString());
        }
        return sb.append(')').toString();
    }

    @Override
    public Object eval(Environment env) {
        throw new StoneException("Cannot eval: " + toString(), this);
    }

}
