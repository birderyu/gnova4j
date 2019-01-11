package gnova.query.expression;

import gnova.core.function.Getter;

public abstract class AbstractInvariableExpression extends AbstractPredicateExpression implements InvariableExpression {

    private final boolean always;

    public AbstractInvariableExpression(boolean always) {
        this.always = always;
    }

    @Override
    public boolean isInvariable() {
        return true;
    }

    @Override
    public boolean match(Getter getter) throws IllegalArgumentException {
        return always;
    }

    @Override
    public boolean isAlwaysTrue() {
        return always;
    }

    @Override
    public boolean isAlwaysFalse() {
        return !always;
    }

    @Override
    public int placeholderSize() {
        return 0;
    }
}
