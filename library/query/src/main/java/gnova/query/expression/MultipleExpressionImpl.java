package gnova.query.expression;

import gnova.core.annotation.NotNull;

public final class MultipleExpressionImpl extends AbstractPredicateExpression implements MultipleExpression {

    @NotNull
    private final PredicateExpression left;

    @NotNull
    private final LogicalPredicate predicate;

    @NotNull
    private final PredicateExpression right;

    public MultipleExpressionImpl(@NotNull PredicateExpression left,
                                  @NotNull LogicalPredicate predicate,
                                  @NotNull PredicateExpression right) {
        this.left = left;
        this.predicate = predicate;
        this.right = right;
    }

    @Override
    public boolean isMultiple() {
        return true;
    }

    @Override
    public PredicateExpression getLeft() {
        return left;
    }

    @Override
    public LogicalPredicate getPredicate() {
        return predicate;
    }

    @Override
    public PredicateExpression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return getLeft().toString() + " " + getPredicate().toString() + " " + toString(getRight());
    }

    private String toString(PredicateExpression predicateExpression) {
        if (predicateExpression.isSimple() || predicateExpression.isNegative()) {
            return predicateExpression.toString();
        }
        return "(" + predicateExpression.toString() + ")";
    }

}
