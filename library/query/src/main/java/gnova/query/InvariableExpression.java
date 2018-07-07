package gnova.query;

import gnova.function.Getter;

public abstract class InvariableExpression
        extends LogicalExpression {

    private final boolean alwaysTrue;

    public InvariableExpression(boolean alwaysTrue) {
        this.alwaysTrue = alwaysTrue;
    }

    @Override
    public boolean fit(Getter getter)
            throws UnsupportedOperationException {
        return alwaysTrue ? true : false;
    }

    @Override
    public boolean isAlwaysTrue() {
        return alwaysTrue;
    }

    @Override
    public boolean isAlwaysFalse() {
        return !alwaysTrue;
    }

    @Override
    public boolean isInvariable() {
        return true;
    }

    @Override
    public InvariableExpression asInvariable() {
        return this;
    }

}
