package gnova.query;

import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * 非
 */
public class NonExpression
        extends LogicalExpression {

    private final LogicalExpression le;

    public NonExpression(@NotNull LogicalExpression le) {
        this.le = le;
    }

    public LogicalExpression getLogicalExpression() {
        return le;
    }

    @Override
    public boolean fit(Getter getter)
            throws IllegalArgumentException {
        return !le.fit(getter);
    }

    @Override
    public boolean isAlwaysTrue() {
        return le.isAlwaysFalse();
    }

    @Override
    public boolean isAlwaysFalse() {
        return le.isAlwaysTrue();
    }

    @Override
    public int sizeOfPlaceholder() {
        return le.sizeOfPlaceholder();
    }

    @Override
    public boolean isNon() { return true; }

    @Override
    public NonExpression asNon() {
        return this;
    }

    @Override
    public String toString() {
        return "!(" + le.toString() + ")";
    }
}