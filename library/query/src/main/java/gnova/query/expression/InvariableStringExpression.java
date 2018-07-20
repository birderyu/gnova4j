package gnova.query.expression;

public class InvariableStringExpression
        extends InvariableExpression {

    private final String expression;

    public InvariableStringExpression(String expression,
                                      boolean alwaysTrue) {
        super(alwaysTrue);
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
