package ch08;

import ch06.BasicInterpreter;
import ch07.NestedEnv;
import stone.ClosureParser;
import stone.ParseException;
import stone.StoneException;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new Natives().environment(new NestedEnv()));
    }
}
