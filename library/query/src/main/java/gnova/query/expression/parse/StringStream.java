package gnova.query.expression.parse;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;

import java.util.Deque;

public class StringStream {

    @NotNull
    private final String value;

    /**
     * 起始下标
     */
    private final int beginIndex;

    /**
     * 终止下标
     */
    private final int endIndex;

    /**
     * 指向当前元素
     */
    private int cursor;

    /**
     * 引号的范围
     */
    private final Deque<QuotationScope> quotationScopes;

    /**
     * 括号的范围
     */
    private final Deque<BracketScope> bracketScopes;

    /**
     *
     * @param value 字符串值
     * @param beginIndex 起始下标
     *                   beginIndex必须大于等于0，并且小于等于endIndex，
     *                   即[0, endIndex]
     * @param endIndex 终止下标的后一位，这是为了与标准库统一
     *                 endIndex必须大于等于beginIndex，并且小于等于value.size()，
     *                 即[beginIndex, value.size()]
     */
    public StringStream(@NotNull String value,
                        @Checked int beginIndex,
                        @Checked int endIndex,
                        Deque<QuotationScope> quotationScopes,
                        Deque<BracketScope> bracketScopes) {
        this.endIndex = endIndex;
        this.value = value;
        this.beginIndex = beginIndex;
        this.cursor = beginIndex;
        this.quotationScopes = quotationScopes;
        this.bracketScopes = bracketScopes;
    }

    public boolean isEmpty() {
        return beginIndex >= endIndex;
    }

    public int size() {
        return endIndex - beginIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public Deque<QuotationScope> getQuotationScopes() {
        return quotationScopes;
    }

    public Deque<BracketScope> getBracketScopes() {
        return bracketScopes;
    }

    public boolean hasPrevious() {
        return hasPreviousN(1);
    }

    public boolean hasPreviousN(int n) {
        return cursor - n + 1 > beginIndex;
    }

    public boolean hasCurrent() {
        return cursor < endIndex;
    }

    public boolean hasNext() {
        return hasNextN(1);
    }

    public boolean hasNextN(int n) {
        return cursor + n - 1 < endIndex - 1;
    }

    /**
     * 移动光标到下一个位置
     */
    public void next() {
        nextN(1);
    }

    /**
     * 移动光标到下n个位置
     *
     * @param n
     */
    public void nextN(int n) {
        cursor += n;
    }

    public void nextTo(int c) {
        cursor = c;
    }

    public int getCursor() {
        return cursor;
    }

    public char getCurrent() {
        return value.charAt(cursor);
    }

    /**
     * 获取当前光标的上n个位置的字符，不移动光标
     *
     * @param n
     * @return
     */
    public char getPreviousN(int n) {
        return value.charAt(cursor - n);
    }

    /**
     * 获取当前光标的下n个位置的字符，不移动光标
     *
     * @param n
     * @return
     */
    public char getNextN(int n) {
        return value.charAt(cursor + n);
    }

    /**
     * 判断当前光标是否位于引号的范围内，
     * 若位于，则返回该引号的范围，否则返回null
     *
     * @return
     */
    public QuotationScope inQuotation() {
        if (quotationScopes == null || quotationScopes.isEmpty()) {
            return null;
        }

        // 引号是不允许嵌套的，因此所有引号一定是按照位置首尾顺次排列的
        if (quotationScopes.getFirst().beforeBy(cursor)) {
            // 当前下标还没有到达第一个引号的位置
            // 直接返回null
            return null;
        }
        if (quotationScopes.getLast().afterBy(cursor)) {
            // 当前下标已经超过了最后一个引号的位置
            // 直接返回null
            return null;
        }
        for (QuotationScope quotationScope : quotationScopes) {
            if (quotationScope.inBy(cursor)) {
                return quotationScope;
            } else if (quotationScope.beforeBy(cursor)) {
                // 当前下标还没有到达当前引号的位置
                // 由于引号无法嵌套，因此下一个引号的位置一定在当前引号之后
                // 下标还没有到达当前引号的位置，那么下标也一定不会到达下一个引号的位置
                // 直接返回null即可
                return null;
            }
        }
        return null;
    }

    /**
     * 判断当前光标是否位于括号的范围内，
     * 若位于，则返回该括号的范围，否则返回null
     *
     * @return
     */
    public BracketScope inBracket() {

        if (bracketScopes == null || bracketScopes.isEmpty()) {
            return null;
        }

        for (BracketScope bracketScope : bracketScopes) {
            if (bracketScope.containsTo(beginIndex, endIndex)) {
                // 括号的范围已经完全包含了当前字符串的有效范围
                // 该括号是无效的
                continue;
            } else if (bracketScope.inBy(cursor)) {
                return bracketScope;
            }
        }
        return null;
    }

    /**
     *
     * @param beginIndex 起始下标
     *                   beginIndex必须大于等于0，并且小于等于endIndex，
     *                   即[0, endIndex]
     * @param endIndex 终止下标的后一位，这是为了与标准库统一
     *                 endIndex必须大于等于beginIndex，并且小于等于value.size()，
     *                 即[beginIndex, value.size()]
     * @return
     */
    public StringStream subString(int beginIndex, int endIndex) {
        int newBeginIndex = this.beginIndex > beginIndex ? this.beginIndex : beginIndex;
        int newEndIndex = this.endIndex < endIndex ? this.endIndex : endIndex;
        return new StringStream(value,
                newBeginIndex, newEndIndex,
                quotationScopes, bracketScopes);
    }

    public StringStream trim() {
        int newBeginIndex = beginIndex;
        while (newBeginIndex < endIndex) {
            if (value.charAt(newBeginIndex) == ' ') {
                newBeginIndex++;
            } else {
                break;
            }
        }
        int newEndIndex = endIndex;
        while (newEndIndex > newBeginIndex) {
            if (value.charAt(newEndIndex - 1) == ' ') {
                newBeginIndex--;
            } else {
                break;
            }
        }
        if (beginIndex == newBeginIndex && endIndex == newEndIndex) {
            return this;
        }
        return new StringStream(value, newBeginIndex, newEndIndex,
                quotationScopes, bracketScopes);
    }

    @Override
    public String toString() {
        if (beginIndex == 0 && endIndex == value.length() - 1) {
            return value;
        }
        return value.substring(beginIndex, endIndex);
    }

    public String getValue() {
        return value;
    }

    public boolean matches(@NotNull String pattern,
                           String prefix,
                           String suffix) {

        // 正文
        if (cursor + pattern.length() > endIndex) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (value.charAt(i + cursor) != pattern.charAt(i)) {
                return false;
            }
        }

        // 前缀
        if (prefix != null && !prefix.isEmpty()) {
            int _cursor = cursor - prefix.length();
            if (_cursor < beginIndex) {
                return false;
            }
            for (int i = 0; i < prefix.length(); i++) {
                if (value.charAt(i + _cursor) != prefix.charAt(i)) {
                    return false;
                }
            }
        }

        // 后缀
        if (suffix != null && !suffix.isEmpty()) {
            int _cursor = cursor + pattern.length();
            if (_cursor + suffix.length() > endIndex) {
                return false;
            }
            for (int i = 0; i < suffix.length(); i++) {
                if (value.charAt(i + _cursor) != suffix.charAt(i)) {
                    return false;
                }
            }
        }

        return true;
    }
}
