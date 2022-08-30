package ch07;

import ch06.Environment;

import java.util.HashMap;

public class NestedEnv implements Environment {
    protected HashMap<String, Object> values;
    protected Environment outer;

    public NestedEnv() {
        this(null);
    }

    public NestedEnv(Environment outer) {
        this.values = new HashMap<>();
        this.outer = outer;
    }

    public void setOuter(Environment outer) {
        this.outer = outer;
    }

    /*
    不去管上层环境是否有指定的变量，直接在当前环境设置新的值（用于在函数内定义同名变量时覆盖）
     */
    public void putNew(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public void put(String name, Object value) {
        Environment e = where(name);
        if (e == null) {
            e = this;
        }
        e.putNew(name, value);
    }

    @Override
    public Object get(String name) {
        Object v = values.get(name);
        if (v == null && outer != null) {
            return outer.get(name);
        } else {
            return v;
        }
    }

    public Environment where(String name) {
        if (values.containsKey(name)) {
            return this;
        } else if (outer == null) {
            return null;
        } else {
            return  outer.where(name);
        }
    }
}
