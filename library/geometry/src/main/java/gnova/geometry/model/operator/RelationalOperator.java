package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;

/**
 * 空间关系操作
 *
 * <p>空间关系操作可以使用DE-9IM（Dimensionally Extended nine-Intersection Model）交叉矩阵进行判断
 * {@see https://en.wikipedia.org/wiki/DE-9IM}
 *
 * <p>DE-9IM矩阵被定义为：
 * <br>| II IB IE |
 * <br>| BI BB BE |
 * <br>| EI EB EE |
 * <br>其中I表示内部的（Interior），B表示边界上的（Boundary），E表示外部的（Exterior），
 * 第一个字母表示左侧的几何对象，第二个字母表示右侧的几何对象，
 * 如IB表示左侧几何对象的内部与右侧几何对象的边界的空间映射关系，其值有以下几种：
 * <br>-1：左右两侧几何对象空间映射的值不相交；
 * <br>0：左右两侧几何对象空间映射的值为一个0维几何对象，如点和多点类型；
 * <br>1：左右两侧几何对象空间映射的值为一个1维几何对象，如线串和多点线串；
 * <br>2：左右两侧几何对象空间映射的值为一个2维几何对象，如多边形和多多边形类型；
 * <br>T：左右两侧几何对象空间映射的值不为-1（即为0、1或2）
 * <br>F：左右两侧几何对象空间映射的值为-1
 * <br>*：以上六个值中的任意值，即不关心这个值。
 *
 * <p>定义如下谓词：
 * <br>contains：包含判断，用于判断左侧几何对象是否包含右侧几何对象；
 * <br>crosses：交叉判断，用于判断左侧几何对象与右侧几何对象是否交叉；
 * <br>equals：拓扑相等判断，用于判断左侧几何对象与右侧几何对象是否拓扑相等；
 * <br>touches：接触判断，用于判断左侧几何对象与右侧几何对象是否接触；
 * <br>intersects：相交判断，用于判断左侧几何对象与右侧几何对象是否相交；
 * <br>disjoint：不相交判断，用于判断左侧几何对象与右侧几何对象是否不相交；
 * <br>within：内部判断，用于判断左侧几何对象是否位于右侧几何对象的内部；
 * <br>overlaps：重叠判断，用于判断左侧几何对象与右侧几何对象是否重叠；
 * <br>covers：覆盖判断，用于判断左侧几何对象是否覆盖右侧几何对象；
 * <br>coveredBy：被覆盖判断，用于判断左侧几何对象是否被右侧几何对象覆盖。
 *
 * @author Birderyu
 * @date 2017/6/21
 */
public interface RelationalOperator {

    /**
     * 判断当前对象（作为左侧）是否包含另一个几何对象（作为右侧）
     *
     * <p>包含判断等于另一个几何对象对当前对象的{@link RelationalOperator#within(Geometry) 内部判断}，
     * 即a.contains(b) == b.within(a)
     *
     * <p>包含判断的DE-9IM矩阵为[T*****FF*]
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#within(Geometry)
     */
    boolean contains(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）是否与另一个几何对象（作为右侧）是否交叉
     *
     * <p>交叉判断首先需要两侧的几何对象维度值不相同，或者两侧的维度值相同且都为1。
     * 当两侧的维度值不相同时，若左侧的维度值小于右侧的维度值，则DE-9IM矩阵为[T*T******]；
     * 若左侧的维度值大于右侧的维度值，则DE-9IM矩阵为[T*****T**]；
     * 若左右两侧的维度值相同且都为1，则DE-9IM矩阵为[0********]。
     * 有25%的DE-9IM矩阵值符合交叉判断的矩阵值，前提是必须满足维度的前提条件，否则这个概率将为0%。
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     */
    boolean crosses(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）与另一个几何对象（作为右侧）是否相等
     *
     * <p>注意，这里的相等指的是{@link RelationalOperator#topologicallyEquals(Geometry) 拓扑相等}，
     * 并非{@link Geometry#exactlyEquals(Geometry) 精确相等}
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#topologicallyEquals(Geometry)
     * @see Geometry#exactlyEquals(Geometry)
     */
    default boolean equals(@NotNull Geometry other) {
        return topologicallyEquals(other);
    }

    /**
     * 判断当前对象（作为左侧）与另一个几何对象（作为右侧）是否拓扑相等
     *
     * <p>拓扑相等判断是{@link RelationalOperator#contains(Geometry) 包含判断}与{@link RelationalOperator#within(Geometry) 内部判断}
     * 结果的交集，即既满足包含判断又满足内部判断的两个对象，是拓扑相等的。
     *
     * <p>拓扑相等判断的DE-9IM矩阵为[T*F**FFF*]，
     * 有3.1%的DE-9IM矩阵值符合拓扑相等判断的矩阵值
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#contains(Geometry)
     * @see RelationalOperator#within(Geometry)
     */
    boolean topologicallyEquals(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）与另一个几何对象（作为右侧）是否接触
     *
     * <p>接触判断的DE-9IM矩阵为[FT*******]、[F**T*****]和[F***T****]，
     * 有43.8%的DE-9IM矩阵值符合接触判断的矩阵值
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     */
    boolean touches(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）与另一个几何对象（作为右侧）是否相交
     *
     * <p>相交判断与{@link RelationalOperator#disjoint(Geometry) 不相交判断}互为逆运算，
     * 即a.intersects(b) = !a.disjoint(b)，
     * 这也意味者相交的集合与不相交的集合之和将会是一个全集，即a.intersects(b) || a.disjoint(b)恒为true
     *
     * <p>相交判断的DE-9IM矩阵为[T********]、[*T*******]、[***T*****]、[****T****]，
     * 有93.7%的DE-9IM矩阵值符合相交判断的矩阵值，是所有谓词中最多的。
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#disjoint(Geometry)
     */
    boolean intersects(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）与另一个几何对象（作为右侧）是否不相交
     *
     * <p>不相交判断与{@link RelationalOperator#intersects(Geometry) 相交判断}互为逆运算，
     * 即a.disjoint(b) = !a.intersects(b)，
     * 这也意味者不相交的集合与相交的集合之和将会是一个全集，即a.disjoint(b) || a.intersects(b)恒为true
     *
     * <p>DE-9IM矩阵为[T*F**FFF*]，
     * 有6.3%的DE-9IM矩阵值符合不相交判断的矩阵值
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#intersects(Geometry)
     */
    boolean disjoint(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）是否位于另一个几何对象（作为右侧）的内部
     *
     * <p>内部判断等于另一个几何对象对当前对象的{@link RelationalOperator#contains(Geometry) 包含判断}，
     * 即a.within(b) == b.contains(a)
     *
     * <p>内部判断的DE-9IM矩阵为[T*F**F***]
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#contains(Geometry)
     */
    boolean within(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）与另一个几何对象（作为右侧）是否重叠
     *
     * <p>重叠判断首先需要两侧的几何对象维度值相同，即两侧的几何对象维度值必须都为0、1或2，
     * 当维度值都为0或2时，DE-9IM矩阵为[T*T***T**]，当维度值都为1时，DE-9IM矩阵为[1*T***T**]
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     */
    boolean overlaps(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）是否覆盖另一个几何对象（作为右侧）
     *
     * <p>覆盖判断等于另一个几何对象对当前对象的{@link RelationalOperator#coveredBy(Geometry) 被覆盖判断}，
     * 即a.covers(b) = b.coveredBy(a)
     *
     * <p>覆盖判断的DE-9IM矩阵为[T*****FF*]、[*T****FF*]、[***T**FF*]和[****T*FF*]，
     * 有23.4%的DE-9IM矩阵值符合覆盖判断的矩阵值
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#coveredBy(Geometry)
     */
    boolean covers(@NotNull Geometry other);

    /**
     * 判断当前对象（作为左侧）是否被另一个几何对象（作为右侧）覆盖
     *
     * <p>被覆盖判断等于另一个几何对象对当前对象的{@link RelationalOperator#covers(Geometry) 覆盖判断}，
     * 即a.coveredBy(b) = b.covers(a)
     *
     * <p>被覆盖判断的DE-9IM矩阵为[T*F**F***]、[*TF**F***]、[**FT*F***]和[**F*TF***]，
     * 有23.4%的DE-9IM矩阵值符合被覆盖判断的矩阵值
     *
     * @param other 几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#covers(Geometry)
     */
    boolean coveredBy(@NotNull Geometry other);

    /**
     * 判断左侧几何对象是否包含右侧几何对象
     *
     * <p>包含判断等于右侧几何对象对左侧几何对象的{@link RelationalOperator#within(Geometry, Geometry) 内部判断}，
     * 即contains(a, b) == within(b, a)
     *
     * <p>包含判断的DE-9IM矩阵为[T*****FF*]
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#within(Geometry, Geometry)
     */
    static boolean contains(@NotNull Geometry left, @NotNull Geometry right) {
        return left.contains(right);
    }

    /**
     * 判断左侧几何对象是否与右侧几何对象是否交叉
     *
     * <p>交叉判断首先需要两侧的几何对象维度值不相同，或者两侧的维度值相同且都为1。
     * 当两侧的维度值不相同时，若左侧的维度值小于右侧的维度值，则DE-9IM矩阵为[T*T******]；
     * 若左侧的维度值大于右侧的维度值，则DE-9IM矩阵为[T*****T**]；
     * 若左右两侧的维度值相同且都为1，则DE-9IM矩阵为[0********]。
     * 有25%的DE-9IM矩阵值符合交叉判断的矩阵值，前提是必须满足维度的前提条件，否则这个概率将为0%。
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     */
    static boolean crosses(@NotNull Geometry left, @NotNull Geometry right) {
        return left.crosses(right);
    }

    /**
     * 判断左侧几何对象与另一个右侧几何对象是否相等
     *
     * <p>注意，这里的相等指的是{@link RelationalOperator#topologicallyEquals(Geometry, Geometry) 拓扑相等}，
     * 并非{@link Geometry#exactlyEquals(Geometry, Geometry) 精确相等}
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#topologicallyEquals(Geometry, Geometry)
     * @see Geometry#exactlyEquals(Geometry, Geometry)
     */
    static boolean equals(@NotNull Geometry left, @NotNull Geometry right) {
        return left.equals(right);
    }

    /**
     * 判断左侧几何对象与另一个右侧几何对象是否拓扑相等
     *
     * <p>拓扑相等判断是{@link RelationalOperator#contains(Geometry, Geometry) 包含判断}与{@link RelationalOperator#within(Geometry, Geometry) 内部判断}
     * 结果的交集，即既满足包含判断又满足内部判断的两个对象，是拓扑相等的。
     *
     * <p>拓扑相等判断的DE-9IM矩阵为[T*F**FFF*]，
     * 有3.1%的DE-9IM矩阵值符合拓扑相等判断的矩阵值
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#contains(Geometry, Geometry)
     * @see RelationalOperator#within(Geometry, Geometry)
     */
    static boolean topologicallyEquals(@NotNull Geometry left, @NotNull Geometry right) {
        return left.topologicallyEquals(right);
    }

    /**
     * 判断左侧几何对象与右侧几何对象是否接触
     *
     * <p>接触判断的DE-9IM矩阵为[FT*******]、[F**T*****]和[F***T****]，
     * 有43.8%的DE-9IM矩阵值符合接触判断的矩阵值
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     */
    static boolean touches(@NotNull Geometry left, @NotNull Geometry right) {
        return left.touches(right);
    }

    /**
     * 判断左侧几何对象与右侧几何对象是否相交
     *
     * <p>相交判断与{@link RelationalOperator#disjoint(Geometry, Geometry) 不相交判断}互为逆运算，
     * 即intersects(a, b) = !disjoint(a, b)，
     * 这也意味者相交的集合与不相交的集合之和将会是一个全集，即intersects(a, b) || disjoint(a, b)恒为true
     *
     * <p>相交判断的DE-9IM矩阵为[T********]、[*T*******]、[***T*****]、[****T****]，
     * 有93.7%的DE-9IM矩阵值符合相交判断的矩阵值，是所有谓词中最多的。
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#disjoint(Geometry, Geometry)
     */
    static boolean intersects(@NotNull Geometry left, @NotNull Geometry right) {
        return left.intersects(right);
    }

    /**
     * 判断左侧几何对象与右侧几何对象是否不相交
     *
     * <p>不相交判断与{@link RelationalOperator#intersects(Geometry, Geometry) 相交判断}互为逆运算，
     * 即disjoint(a, b) = !intersects(a, b)，
     * 这也意味者不相交的集合与相交的集合之和将会是一个全集，即disjoint(a, b) || intersects(a, b)恒为true
     *
     * <p>DE-9IM矩阵为[T*F**FFF*]，
     * 有6.3%的DE-9IM矩阵值符合不相交判断的矩阵值
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#intersects(Geometry, Geometry)
     */
    static boolean disjoint(@NotNull Geometry left, @NotNull Geometry right) {
        return left.disjoint(right);
    }

    /**
     * 判断左侧几何对象是否位于右侧几何对象的内部
     *
     * <p>内部判断等于右侧几何对象对左侧几何对象的{@link RelationalOperator#contains(Geometry, Geometry) 包含判断}，
     * 即within(a, b) == contains(b, a)
     *
     * <p>内部判断的DE-9IM矩阵为[T*F**F***]
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#contains(Geometry, Geometry)
     */
    static boolean within(@NotNull Geometry left, @NotNull Geometry right) {
        return left.within(right);
    }

    /**
     * 判断左侧几何对象与另右侧几何对象是否重叠
     *
     * <p>重叠判断首先需要两侧的几何对象维度值相同，即两侧的几何对象维度值必须都为0、1或2，
     * 当维度值都为0或2时，DE-9IM矩阵为[T*T***T**]，当维度值都为1时，DE-9IM矩阵为[1*T***T**]
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     */
    static boolean overlaps(@NotNull Geometry left, @NotNull Geometry right) {
        return left.overlaps(right);
    }

    /**
     * 判断左侧几何对象是否覆盖右侧几何对象
     *
     * <p>覆盖判断等于右侧几何对象对左侧几何对象的{@link RelationalOperator#coveredBy(Geometry, Geometry) 被覆盖判断}，
     * 即covers(a, b) = coveredBy(b, a)
     *
     * <p>覆盖判断的DE-9IM矩阵为[T*****FF*]、[*T****FF*]、[***T**FF*]和[****T*FF*]，
     * 有23.4%的DE-9IM矩阵值符合覆盖判断的矩阵值
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#coveredBy(Geometry, Geometry)
     */
    static boolean covers(@NotNull Geometry left, @NotNull Geometry right) {
        return left.covers(right);
    }

    /**
     * 判断左侧几何对象是否被右侧几何对象覆盖
     *
     * <p>被覆盖判断等于右侧几何对象对左侧几何对象的{@link RelationalOperator#covers(Geometry, Geometry) 覆盖判断}，
     * 即coveredBy(a, b) = covers(b, a)
     *
     * <p>被覆盖判断的DE-9IM矩阵为[T*F**F***]、[*TF**F***]、[**FT*F***]和[**F*TF***]，
     * 有23.4%的DE-9IM矩阵值符合被覆盖判断的矩阵值
     *
     * @param left 左侧几何对象，不允许为null
     * @param right 右侧几何对象，不允许为null
     * @return 若满足断言，则返回true，否则返回false
     * @see RelationalOperator#covers(Geometry, Geometry)
     */
    static boolean coveredBy(@NotNull Geometry left, @NotNull Geometry right) {
        return left.coveredBy(right);
    }

}
