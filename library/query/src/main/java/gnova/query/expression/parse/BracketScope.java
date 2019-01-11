package gnova.query.expression.parse;

import gnova.core.EmptyIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 括号的范围
 */
public class BracketScope
        extends StringScope {

    /**
     * 符号的类型
     */
    public enum Type {

        /**
         * 圆括号
         */
        Parenthesis,

        /**
         * 方括号
         */
        Square,

        /**
         * 花括号
         */
        Curly,
    }

    /**
     * 括号的类型
     */
    private final Type type;

    /**
     * 上层括号
     */
    private final BracketScope parent;

    /**
     * 下层括号
     */
    private Collection<BracketScope> children;

    public BracketScope(BracketScope parent, Type type, int beginIndex) {
        super(beginIndex);
        this.parent = parent;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    /**
     * 是否是圆括号
     *
     * @return
     */
    public boolean isParenthesis() {
        return type == Type.Parenthesis;
    }

    /**
     * 是否是方括号
     *
     * @return
     */
    public boolean isSquare() {
        return type == Type.Square;
    }

    /**
     * 是否是花括号
     *
     * @return
     */
    public boolean isCurly() {
        return type == Type.Curly;
    }

    public BracketScope getParent() {
        return parent;
    }

    public Iterator<BracketScope> getChildren() {
        return children == null ? new EmptyIterator<>() : children.iterator();
    }

    public void addChild(BracketScope child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }
}
