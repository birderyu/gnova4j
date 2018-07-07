package gnova.query;

import gnova.util.ArrayIterator;

import java.util.Iterator;

/**
 * 逻辑操作符
 */
public enum LogicalOperator {

    /**
     * 与操作
     */
    And("and", " ", " ") {
        @Override
        public boolean isAnd() {
            return true;
        }
        @Override
        public boolean isOr() {
            return false;
        }
        @Override
        public boolean isXor() {
            return false;
        }
        @Override
        public boolean shortFor(boolean l) {
            // 如果left为false，则短路
            return !l;
        }
    },

    /**
     * 或操作
     */
    Or("or", " ", " ") {
        @Override
        public boolean isAnd() {
            return false;
        }
        @Override
        public boolean isOr() {
            return true;
        }
        @Override
        public boolean isXor() {
            return false;
        }
        @Override
        public boolean shortFor(boolean l) {
            // 如果left为true，则短路
            return l;
        }
    },

    /**
     * 异或操作
     */
    Xor("xor", " ", " ") {
        @Override
        public boolean isAnd() {
            return false;
        }
        @Override
        public boolean isOr() {
            return false;
        }
        @Override
        public boolean isXor() {
            return true;
        }
    },

    /**
     * &&
     */
    DoubleAndSymbol("&&", null, null) {
        @Override
        public boolean isAnd() {
            return true;
        }
        @Override
        public boolean isOr() {
            return false;
        }
        @Override
        public boolean isXor() {
            return false;
        }
        @Override
        public boolean shortFor(boolean l) {
            // 如果left为false，则短路
            return !l;
        }
    },

    /**
     * ||
     */
    DoubleOrSymbol("||", null, null) {
        @Override
        public boolean isAnd() {
            return false;
        }
        @Override
        public boolean isOr() {
            return true;
        }
        @Override
        public boolean isXor() {
            return false;
        }
        @Override
        public boolean shortFor(boolean l) {
            // 如果left为true，则短路
            return l;
        }
    },

    /**
     * &
     */
    SingleAndSymbol("&", null, null) {
        @Override
        public boolean isAnd() {
            return true;
        }
        @Override
        public boolean isOr() {
            return false;
        }
        @Override
        public boolean isXor() {
            return false;
        }
    },

    /**
     * |
     */
    SingleOrSymbol("|", null, null) {
        @Override
        public boolean isAnd() {
            return false;
        }
        @Override
        public boolean isOr() {
            return true;
        }
        @Override
        public boolean isXor() {
            return false;
        }
    },

    /**
     * ;
     */
    Semicolon(";", null, null) {
        @Override
        public boolean isAnd() {
            return true;
        }
        @Override
        public boolean isOr() {
            return false;
        }
        @Override
        public boolean isXor() {
            return false;
        }
        @Override
        public boolean shortFor(boolean l) {
            // 如果left为false，则短路
            return !l;
        }
    };

    private final String symbol;
    private final String prefix;
    private final String suffix;

    LogicalOperator(String symbol, String prefix, String suffix) {
        this.symbol = symbol;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * 是否是逻辑与操作
     *
     * @return
     */
    public abstract boolean isAnd();

    /**
     * 是否是逻辑或操作
     *
     * @return
     */
    public abstract boolean isOr();

    /**
     * 是否是逻辑异或操作
     *
     * @return
     */
    public abstract boolean isXor();

    /**
     * 判断短路
     *
     * 若返回true，表示短路，此时逻辑值应该与l相同
     *
     * @param l
     * @return
     */
    public boolean shortFor(boolean l) {
        return false;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public String toString() {
        return symbol;
    }

    private static final LogicalOperator[] matchOrder = new LogicalOperator[] {
            And, DoubleAndSymbol, SingleAndSymbol, Semicolon,
            Or, DoubleOrSymbol, SingleOrSymbol,
            Xor
    };

    public static Iterator<LogicalOperator> getMatchOrder() {
        return new ArrayIterator<>(matchOrder);

    }

}
