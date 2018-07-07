package gnova.query;

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
    public int sizeOfPlaceholder() {
        return logicalExpression.sizeOfPlaceholder();
    }

    @Override
    public String toString() {
        return logicalExpression.toString();
    }
}
