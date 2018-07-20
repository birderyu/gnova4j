package gnova.query.expression.parse;

/**
 * 引号的范围
 */
public class QuotationScope extends StringScope {

    public QuotationScope(int beginIndex, int endIndex) {
        super(beginIndex);
        setEndIndex(endIndex);
    }
}
