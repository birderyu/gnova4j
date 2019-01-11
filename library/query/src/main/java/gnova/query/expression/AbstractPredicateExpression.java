package gnova.query.expression;

public abstract class AbstractPredicateExpression implements PredicateExpression {

    @Override
    public boolean isInvariable() {
        return false;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public boolean isNegative() { return false; }

    @Override
    public InvariableExpression asInvariable() {
        return isInvariable() ? (InvariableExpression) this : null;
    }

    @Override
    public SimpleExpression asSimple() {
        return isSimple() ? (SimpleExpression) this : null;
    }

    @Override
    public MultipleExpression asMultiple() {
        return isMultiple() ? (MultipleExpression) this : null;
    }

    @Override
    public NegativeExpression asNegative() {
        return isNegative() ? (NegativeExpression) this : null;
    }

}
