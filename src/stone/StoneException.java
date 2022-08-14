package stone;

import stone.ast.ASTree;

/**
 * @author 肖皓星
 * @email xiaohaoxing@outlook.com
 */
public class StoneException extends RuntimeException {
    public StoneException(String m) {
        super(m);
    }

    public StoneException(String m, ASTree t) {
        super(m + " " + t.location());
    }
}
