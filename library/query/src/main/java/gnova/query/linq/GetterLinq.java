package gnova.query.linq;

import gnova.annotation.NotNull;
import gnova.query.LogicalExpression;
import gnova.function.Getter;

public interface GetterLinq<E extends Getter, R>
        extends Linq<E, R> {

    /**
     * 接受一个逻辑表达式作为谓词
     *
     * @param expression
     * @return
     */
    Linq<E, R> where(@NotNull LogicalExpression expression);

}
