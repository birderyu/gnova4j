package gnova.query.expression;

import gnova.core.function.Getter;

/**
 * 恒定的谓词表达式
 */
public interface InvariableExpression extends PredicateExpression {

    /**
     * 一个空的恒真逻辑表达式
     */
    InvariableExpression ALWAYS_TRUE = new InvariableExpression() {

        @Override
        public boolean match(Getter getter) throws IllegalArgumentException {
            return true;
        }

        @Override
        public boolean isAlwaysTrue() {
            return true;
        }

        @Override
        public boolean isAlwaysFalse() {
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
        public boolean isNegative() {
            return false;
        }

        @Override
        public SimpleExpression asSimple() {
            return null;
        }

        @Override
        public MultipleExpression asMultiple() {
            return null;
        }

        @Override
        public NegativeExpression asNegative() {
            return null;
        }

        @Override
        public int placeholderSize() {
            return 0;
        }

        @Override
        public String toString() {
            return "true";
        }
    };

    /**
     * 一个空的恒假逻辑表达式
     */
    InvariableExpression ALWAYS_FALSE = new InvariableExpression() {

        @Override
        public boolean match(Getter getter) throws IllegalArgumentException {
            return false;
        }

        @Override
        public boolean isAlwaysTrue() {
            return false;
        }

        @Override
        public boolean isAlwaysFalse() {
            return true;
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
        public boolean isNegative() {
            return false;
        }

        @Override
        public SimpleExpression asSimple() {
            return null;
        }

        @Override
        public MultipleExpression asMultiple() {
            return null;
        }

        @Override
        public NegativeExpression asNegative() {
            return null;
        }

        @Override
        public int placeholderSize() {
            return 0;
        }

        @Override
        public String toString() {
            return "false";
        }
    };

    @Override
    default boolean isInvariable() {
        return true;
    }

    @Override
    default InvariableExpression asInvariable() {
        return this;
    }

}
