package gnova.query.expression.parse;

/**
 * 字符串的范围
 */
public class StringScope {

    private final int beginIndex;
    private int endIndex = -1;

    public StringScope(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex)
            throws IllegalArgumentException {
        if (endIndex <= beginIndex) {
            throw new IllegalArgumentException("endIndex（"
                    + endIndex
                    + "）必须大于beginIndex（"
                    + beginIndex
                    + "）");
        }
        this.endIndex = endIndex;
    }

    /**
     * 当前范围是否完全包含了给定的范围
     *
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public boolean containsTo(int beginIndex, int endIndex) {
        return (this.beginIndex < beginIndex && this.endIndex >= endIndex) ||
                (this.beginIndex <= beginIndex && this.endIndex > endIndex) ||
                (this.beginIndex < beginIndex && this.endIndex > endIndex);
    }

    /**
     * 下标是否位于范围之前
     *
     * @param index
     * @return
     */
    public boolean beforeBy(int index) {
        return index < beginIndex;
    }

    /**
     * 下标是否位于范围之后
     *
     * @param index
     * @return
     */
    public boolean afterBy(int index) {
        return index >= endIndex;
    }

    /**
     * 下标是否位于范围之内
     *
     * @param index
     * @return
     */
    public boolean inBy(int index) {
        return index >= beginIndex && index < endIndex;
    }

}
