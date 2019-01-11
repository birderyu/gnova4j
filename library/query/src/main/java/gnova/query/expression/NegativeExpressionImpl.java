package gnova.query.expression;

import gnova.core.annotation.NotNull;

public final class NegativeExpressionImpl extends AbstractPredicateExpression implements NegativeExpression {

    private final PredicateExpression positive;

    public NegativeExpressionImpl(@NotNull PredicateExpression positive) {
        this.positive = positive;
    }

    @Override
    public PredicateExpression getPositive() {
        return positive;
    }

    @Override
    public boolean isNegative() { return true; }

    @Override
    public String toString() {
        return "!(" + getPositive().toString() + ")";
    }

}
