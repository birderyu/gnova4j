package gnova.query.parse;

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
     * 是否是圆括号
     */
    private final boolean parenthesis;

    private final BracketScope parent;

    private Collection<BracketScope> children;

    public BracketScope(BracketScope parent, boolean parenthesis, int beginIndex) {
        super(beginIndex);
        this.parent = parent;
        this.parenthesis = parenthesis;
    }

    public boolean isParenthesis() {
        return parenthesis;
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
