package gnova.query.expression;

public class InvariableStringExpression
        extends AbstractInvariableExpression implements InvariableExpression {

    private final String expression;

    public InvariableStringExpression(String expression,
                                      boolean always) {
        super(always);
        this.expression = expression;
    }

    @Override
    public int placeholderSize() {
        return 0;
    }

    @Override
    public String toString() {
        return expression;
    }
}
