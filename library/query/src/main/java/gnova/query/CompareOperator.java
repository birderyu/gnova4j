package gnova.query;

import gnova.core.annotation.NotNull;
import gnova.core.ArrayIterator;

import java.util.Iterator;

/**
 * 比较操作符
 */
public enum CompareOperator {

    /**
     * 等于
     */
    EQ("=", null, null) {
        @Override
        public CompareOperator getInverse() {
            return NEQ;
        }
    },

    /**
     * 不等于
     */
    NEQ("!=", null, null) {
        @Override
        public CompareOperator getInverse() {
            return EQ;
        }
    },

    /**
     * 小于
     */
    LT("<", null, null) {
        @Override
        public CompareOperator getInverse() {
            return GTE;
        }
    },

    /**
     * 小于等于
     */
    LTE("<=", null, null) {
        @Override
        public CompareOperator getInverse() {
            return GT;
        }
    },

    /**
     * 大于
     */
    GT(">", null, null) {
        @Override
        public CompareOperator getInverse() {
            return LTE;
        }
    },

    /**
     * 大于等于
     */
    GTE(">=", null, null) {
        @Override
        public CompareOperator getInverse() {
            return LT;
        }
    },

    /**
     * 字符串模糊匹配
     */
    LIKE("like", " ", " "),

    /**
     * 列表包含
     */
    IN("in", " ", " ") {
        @Override
        public CompareOperator getInverse() {
            return NIN;
        }
    },

    /**
     * 列表不包含
     */
    NIN("nin", " ", " ") {
        @Override
        public CompareOperator getInverse() {
            return IN;
        }
    },

    /**
     * 几何相交
     */
    INTERSECT("intersect", " ", " "),

    /**
     * 几何包含
     */
    WITHIN("within", " ", " ");

    private final String symbol;
    private final String prefix;
    private final String suffix;

    CompareOperator(String symbol, String prefix, String suffix) {
        this.symbol = symbol;
        this.prefix = prefix;
        this.suffix = suffix;
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

    /**
     * 获取当前比较操作符的逆运算操作符
     * 若无则返回null
     *
     * @return
     */
    public CompareOperator getInverse() {
        return null;
    }

    @Override
    public String toString() {
        return symbol;
    }

    private static final CompareOperator[] matchOrder = new CompareOperator[] {
            EQ, NEQ,
            LTE, LT,
            GTE, GT,
            LIKE,
            IN, NIN,
            INTERSECT, WITHIN
    };

    public static Iterator<CompareOperator> getMatchOrder() {
        return new ArrayIterator<>(matchOrder);
    }

    /**
     * 判断两个比较操作符是否为互逆运算
     *
     * @param co1
     * @param co2
     * @return
     */
    public static boolean isInverse(@NotNull CompareOperator co1,
                                    @NotNull CompareOperator co2) {
        if (co1.getInverse() == null || co2.getInverse() == null) {
            return false;
        }
        return co1.getInverse() == co2 &&
                co2.getInverse() == co1;
    }

    /**
     * 获取两个比较操作符中更加严格的一个
     *
     * 比如 > 和 >=，>更为严格
     * 若第一个操作符更严格，则返回1，若第二个操作符更严格，则返回2
     * 若二者严格的程度相同，则返回3
     * 若二者并无任何严格关系，则返回0
     *
     * @param co1
     * @param co2
     * @return
     */
    public static int getMoreStrict(@NotNull CompareOperator co1,
                                    @NotNull CompareOperator co2) {

        switch (co1) {
            case LT:
                return co2 == LTE ? 1 : co2 == LT ? 3 : 0;
            case LTE:
                return co2 == LT ? 2 : co2 == LTE ? 3 : 0;
            case GT:
                return co2 == GTE ? 1 : co2 == GT ? 3 : 0;
            case GTE:
                return co2 == GT ? 2 : co2 == GTE ? 3 : 0;
            case INTERSECT:
                return co2 == WITHIN ? 2 : co2 == INTERSECT ? 3 : 0;
            case WITHIN:
                return co2 == INTERSECT ? 1 : co2 == WITHIN ? 3 : 0;
        }
        return 0;
    }

}
