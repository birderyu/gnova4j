package gnova.query.expression;

import gnova.core.annotation.NotNull;

public class InvariableProxyExpression
        extends InvariableExpression {

    private final LogicalExpression logicalExpression;

    public InvariableProxyExpression(@NotNull LogicalExpression logicalExpression,
                                     boolean alwaysTrue) {
        super(alwaysTrue);
        this.logicalExpression = logicalExpression;
    }

    @Override
    public int placeholderSize() {
        return logicalExpression.placeholderSize();
    }

    @Override
    public String toString() {
        return logicalExpression.toString();
    }
}
