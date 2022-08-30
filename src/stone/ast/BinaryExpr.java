package stone.ast;

import ch06.Environment;
import stone.StoneException;

import java.util.List;

public class BinaryExpr extends ASTList {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public BinaryExpr(List<ASTree> c) {
        super(c);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token.getText();
    }

    public ASTree right() {
        return child(2);
    }

    @Override
    public Object eval(Environment env) {
        String op = operator();
        if ("=".equals(op)) {
            Object right = right().eval(env);
            return computeAssign(env, right);
        } else {
            Object left = left().eval(env);
            Object right = right().eval(env);
            return computeOp(left, op, right);
        }
    }

    protected Object computeAssign(Environment env, Object rValue) {
        ASTree l = left();
        if (l instanceof Name) {
            env.put(((Name) l).name(), rValue);
            return rValue;
        } else {
            throw new StoneException("Bad assignment", this);
        }
    }

    protected Object computeOp(Object left, String op, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return computeNumber((Integer) left, op, (Integer) right);
        } else {
            if ("+".equals(op)) {
                return String.valueOf(left) + String.valueOf(right);
            } else if ("==".equals(op)) {
                if (left == null) {
                    return right == null ? TRUE : FALSE;
                } else {
                    return left.equals(right) ? TRUE : FALSE;
                }
            } else {
                throw new StoneException("Bad type", this);
            }
        }
    }

    protected Object computeNumber(Integer left, String op, Integer right) {
        int a = left.intValue();
        int b = right.intValue();
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "%":
                return a % b;
            case "==":
                return a == b ? TRUE : FALSE;
            case ">":
                return a > b ? TRUE : FALSE;
            case "<":
                return a < b ? TRUE : FALSE;
            case ">=":
                return a >= b ? TRUE : FALSE;
            case "<=":
                return a <= b ? TRUE : FALSE;
            default:
                throw new StoneException("Bad operator", this);
        }
    }
}
