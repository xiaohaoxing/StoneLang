package stone.ast;

import ch06.Environment;
import ch07.Function;
import ch08.NativeFunction;
import stone.StoneException;

import java.util.List;

public class Arguments extends Postfix {
    public Arguments(List<ASTree> c) {
        super(c);
    }

    public int size() {
        return numChildren();
    }

    public Object eval(Environment callerEnv, Object value) {
        if (value instanceof NativeFunction) {
            NativeFunction func = (NativeFunction) value;
            int nparams = func.numOfParameters();
            if (size() != nparams) {
                throw new StoneException("Bad Number of Arguments", this);
            }
            Object[] args = new Object[nparams];
            int num = 0;
            for (ASTree a : this) {
                args[num++] = a.eval(callerEnv);
            }
            return func.invoke(args, this);
        }
        if (!(value instanceof Function)) {
            throw new StoneException("Bad function", this);
        }
        Function func = (Function) value;
        ParameterList params = func.parameters();
        if (size() != params.size()) {
            throw new StoneException("Bad Number of Arguments", this);
        }
        Environment newEnv = func.makeEnv();
        int num = 0;
        // 先计算实参地方的表达式
        for (ASTree a : this) {
            params.eval(newEnv, num++, a.eval(callerEnv));
        }
        // 再计算具体的函数体的 body
        return func.body().eval(newEnv);
    }
}
