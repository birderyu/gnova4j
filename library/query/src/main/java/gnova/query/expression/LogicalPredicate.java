package gnova.query.expression;

import gnova.core.ArrayIterator;

import java.util.Iterator;

/**
 * 逻辑谓词
 */
public enum LogicalPredicate {

    /**
     * 与操作
     */
    And((byte) 41, "and", " ", " ") {
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
    Or((byte) 42, "or", " ", " ") {
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
    Xor((byte) 43, "xor", " ", " ") {
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
    DoubleAndSymbol((byte) 44, "&&", null, null) {
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
    DoubleOrSymbol((byte) 45, "||", null, null) {
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
    SingleAndSymbol((byte) 46, "&", null, null) {
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
    SingleOrSymbol((byte) 47, "|", null, null) {
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
    Semicolon((byte) 48, ";", null, null) {
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

    private final byte type;
    private final String symbol;
    private final String prefix;
    private final String suffix;

    /**
     *
     * @param symbol 符号
     * @param prefix 前缀
     * @param suffix 后缀
     */
    LogicalPredicate(byte type, String symbol, String prefix, String suffix) {
        this.type = type;
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

    private static final LogicalPredicate[] matchOrder = new LogicalPredicate[] {
            And, DoubleAndSymbol, SingleAndSymbol, Semicolon,
            Or, DoubleOrSymbol, SingleOrSymbol,
            Xor
    };

    public static Iterator<LogicalPredicate> getMatchOrder() {
        return new ArrayIterator<>(matchOrder);

    }

}
