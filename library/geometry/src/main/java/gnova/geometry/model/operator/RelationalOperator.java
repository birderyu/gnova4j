package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;

/**
 * 空间关系操作
 *
 * @author Birderyu
 * @date 2017/6/21
 */
public interface RelationalOperator {

    boolean contains(@NotNull Geometry other);

    boolean crosses(@NotNull Geometry other);

    /**
     * 判断几何对象是否相等
     *
     * @param other
     * @return
     */
    boolean equals(@NotNull Geometry other);

    boolean touches(@NotNull Geometry other);

    boolean disjoint(@NotNull Geometry other);

    boolean intersects(@NotNull Geometry other);

    boolean within(@NotNull Geometry other);

    boolean overlaps(@NotNull Geometry other);

    boolean covers(@NotNull Geometry other);

    boolean coveredBy(@NotNull Geometry other);
}
