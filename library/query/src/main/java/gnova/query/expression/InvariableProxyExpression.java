package gnova.query.expression;

import gnova.core.annotation.NotNull;

public class InvariableProxyExpression
        extends AbstractInvariableExpression implements InvariableExpression {

    private final PredicateExpression predicateExpression;

    public InvariableProxyExpression(@NotNull PredicateExpression predicateExpression,
                                     boolean always) {
        super(always);
        this.predicateExpression = predicateExpression;
    }

    @Override
    public int placeholderSize() {
        return predicateExpression.placeholderSize();
    }

    @Override
    public String toString() {
        return predicateExpression.toString();
    }
}
