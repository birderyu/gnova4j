package gnova.query.expression;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.query.expression.value.KeyExpression;

public class Simplifier {

    public static Expression simplify(@NotNull Expression expression) {

        if (expression.isValue()) {
            return expression;
        }
        return simplifyLogical(expression.asLogical());

    }

    private static LogicalExpression simplifyLogical(@NotNull LogicalExpression le) {

        if (le.isInvariable()) {
            // 恒定的表达式，直接返回
            return le;
        } else if (le.isAlwaysTrue()) {
            // 恒为true的表达式，直接返回
            return Builder.buildInvariable(le, true);
        } else if (le.isAlwaysFalse()) {
            // 恒为false的表达式，直接返回
            return Builder.buildInvariable(le, false);
        } else if (le.isSimple()) {
            // 简单表达式，且不恒等，无法化简，直接返回
            return le;
        } else if (le.isNon()) {
            // 否定表达式，可以化简
            return simplifyNon(le.asNon());
        } else {
            // 复合表达式，可以化简
            return simplifyMulti(le.asMulti());
        }

    }

    private static LogicalExpression simplifyNon(@NotNull NonExpression ne) {
        if (ne.getLogicalExpression().isNon()) {
            // 双重否定，化简
            return simplifyLogical(ne.getLogicalExpression().asNon().getLogicalExpression());
        } else {
            // 化简其逆向的表达式，然后用化简的结果重构一个表达式
            return Builder.buildNon(simplifyLogical(ne.getLogicalExpression()));
        }
    }

    private static LogicalExpression simplifyMulti(@NotNull MultiExpression me) {

        LogicalExpression left = me.getLeftExpression();
        LogicalExpression right = me.getRightExpression();
        LogicalOperator lo = me.getLogicalOperator();

        // 恒等性判断
        if (left.isAlwaysTrue()) {
            // left是恒为true的，此时不可能是逻辑或操作
            if (lo.isAnd()) {
                // 逻辑与操作，且左侧为true，此时表达式的结果取决于右侧
                return simplifyLogical(right);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且左侧为true，此时表达式的结果取决于右侧
                return simplifyLogical(right);
            }
        } else if (left.isAlwaysFalse()) {
            // left是恒为false的，此时不可能是逻辑与操作
            if (lo.isOr()) {
                // 逻辑或操作，且左侧为false，此时表达式的结果取决于右侧
                return simplifyLogical(right);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且左侧为false，此时表达式的结果取决于右侧
                // 但是此时表达式的结果是右侧结果的逻辑非结果
                return Builder.buildNon(simplifyLogical(right));
            }
        }

        if (right.isAlwaysTrue()) {
            // right是恒为true的，此时不可能是逻辑或操作
            if (lo.isAnd()) {
                // 逻辑与操作，且右侧为true，此时表达式的结果取决于左侧
                return simplifyLogical(left);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且右侧为true，此时表达式的结果取决于左侧
                return simplifyLogical(left);
            }
        } else if (right.isAlwaysFalse()) {
            // right是恒为false的，此时不可能是逻辑与操作
            if (lo.isOr()) {
                // 逻辑或操作，且右侧为false，此时表达式的结果取决于左侧
                return simplifyLogical(left);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且右侧为false，此时表达式的结果取决于左侧
                // 但是此时表达式的结果是左侧结果的逻辑非结果
                return Builder.buildNon(simplifyLogical(left));
            }
        }


        if (left.isSimple() && right.isSimple()) {

            return simplifyMulti(me, left.asSimple(), lo, right.asSimple());

        } else {

            // 分别简化左右两侧
            LogicalExpression _left = simplifyLogical(left);
            LogicalExpression _right = simplifyLogical(right);
            if (_left.isSimple() && _right.isSimple()) {
                // 还可以继续简化
                return simplifyMulti(null,
                        _left.asSimple(), lo, _right.asSimple());
            } else {
                // 不可以再简化了
                return Builder.buildMulti(_left, lo, _right);
            }
        }

    }

    private static LogicalExpression simplifyMulti(LogicalExpression me,
                                                   @NotNull SimpleExpression left,
                                                   @NotNull LogicalOperator lo,
                                                   @NotNull SimpleExpression right) {

        if (me == null) {
            me = Builder.buildMulti(left, lo, right);
        }

        // 左右两侧都是简单表达式，并且左右两侧都不会是恒true或恒false了
        // 这说明左右两侧都是键值表达式了
        if (left.placeholderSize() != 0 || right.placeholderSize() != 0) {
            // 左右两侧有占位符，不可能再简化了
            return me;
        }

        // 以下开始，左右两侧都不会有占位符了
        ValueExpression left_key = left.asSimple().getLeftValue();
        ValueExpression right_key = right.asSimple().getLeftValue();
        if (!left_key.asKey().equals(right_key.asKey())) {
            // 左右两侧的键不想等，不可能再简化了
            return me;
        }

        // 以下开始，左右两侧的键是相等的
        ValueExpression left_value = left.asSimple().getRightValue();
        CompareOperator left_co = left.asSimple().getCompareOperator();
        ValueExpression right_value = right.asSimple().getRightValue();
        CompareOperator right_co = right.asSimple().getCompareOperator();

        // 1. 判断等于、不等于、列表包含、列表不包含
        if (left_co == CompareOperator.EQ &&
                right_co == CompareOperator.EQ) {
            // 左右两侧都是等于操作
            LogicalExpression res = simplifyEquals(me, left,
                    (KeyExpression) left_key, left_value, right_value, lo);
            if (res != null) {
                return res;
            }
        } else if (left_co == CompareOperator.NEQ &&
                right_co == CompareOperator.NEQ) {
            // 左右两侧都是不等于操作
            // TODO
        } else if (left_co == CompareOperator.EQ &&
                right_co == CompareOperator.IN) {
            // 左侧是等于操作，右侧是列表包含操作
            // TODO
        }

        // 2. 判断互逆运算
        if (CompareOperator.isInverse(left_co, right_co)) {
            // 若二者互为逆运算，下面判断二者的值是否相等
            LogicalExpression res = simplifyInverse(me,
                    left_value, right_value, lo);
            if (res != null) {
                return res;
            }
        }

        // 3. 判断比较符号的严格性
        int moreStrictCo = CompareOperator.getMoreStrict(left_co, right_co);
        LogicalExpression res = simplifyMoreStrict(moreStrictCo,
                left, lo, right,
                left_value, left_co, right_value, right_co);
        if (res != null) {
            return res;
        }

        // 4. 判断符号的方向性质
        // 若不等号是反向的，则

        // TODO

        return me;

    }


    /**
     *
     * @param lv 不包含占位符，且不为键值
     * @param rv 不包含占位符，且不为键值
     * @return
     */
    private static boolean equals(@Checked ValueExpression lv,
                                  @Checked ValueExpression rv) {
        return (lv.isNull() && rv.isNull()) ||
                ((!lv.isNull() && !rv.isNull()) &&
                        (lv.getValue().equals(rv.getValue())));
    }

    private static LogicalExpression simplifyEquals(@NotNull LogicalExpression me,
                                                    @NotNull SimpleExpression se,
                                                    @NotNull KeyExpression key,
                                                    @NotNull ValueExpression lv,
                                                    @NotNull ValueExpression rv,
                                                    @NotNull LogicalOperator lo) {
        // 左右两侧都是等于操作，下面判断二者的值是否相等
        if (equals(lv, rv)) {
            // 左右两侧的值完全一致
            if (lo.isXor()) {
                // 逻辑异或操作，此时表达式是恒等的，如
                // a = null xor a = null
                // a = 3 xor a = 3
                return Builder.buildInvariable(me, true);
            } else {
                // 其他逻辑运算，简化为其中一个即可，如
                // a = null or a = null -> a = null
                // a = 3 and a = 3 -> a = null
                return se;
            }
        } else {
            // 左右两侧的值不一致
            if (lo.isAnd()) {
                // 逻辑与操作，此时表达式是恒不等的，如
                // a = null and a = 1 -> false
                // a = 1 and a = 2 -> false
                return Builder.buildInvariable(me, false);
            } else if (lo.isOr()) {
                // 逻辑或操作，此时表达式可以合并成一个列表表达式，如
                // a = null or a = 1 -> a in (null, 1)
                // a = null or a = 1 -> a in (null, 1)
                return Builder.buildSimple(key,
                        CompareOperator.IN,
                        Builder.buildList(new ValueExpression[] {lv, rv}));
            }
        }
        return null;
    }

    private static LogicalExpression simplifyInverse(@NotNull LogicalExpression me,
                                                     @NotNull ValueExpression lv,
                                                     @NotNull ValueExpression rv,
                                                     @NotNull LogicalOperator lo) {
        if (equals(lv, rv)) {
            // 左右两侧的值完全一致
            if (lo.isAnd()) {
                // and，恒不等，如：
                // age > 20 and age <= 20 -> false
                // age = 10 and age != 10 -> false
                return Builder.buildInvariable(me, false);
            } else if (lo.isOr()) {
                // or，恒等，如：
                // age > 20 or age <= 20 -> true
                // age = 10 or age != 10 -> true
                return Builder.buildInvariable(me, true);
            } else if (lo.isXor()) {
                // xor，恒等，如：
                // age > 20 xor age <= 20 -> true
                // age = 10 xor age != 10 -> true
                return Builder.buildInvariable(me, true);
            }
        }
        return null;
    }

    private static LogicalExpression simplifyMoreStrict(int moreStrictCo,
                                                        @NotNull SimpleExpression left,
                                                        @NotNull LogicalOperator lo,
                                                        @NotNull SimpleExpression right,
                                                        @NotNull ValueExpression left_value,
                                                        @NotNull CompareOperator left_co,
                                                        @NotNull ValueExpression right_value,
                                                        @NotNull CompareOperator right_co) {
        if (moreStrictCo != 0) {
            // moreStrictCo != 0，说明左右两侧是有严格的关系的
            if (equals(left_value, right_value)) {
                // 左右两侧的值完全一致
                if (lo.isAnd()) {
                    // and操作，取更加严格的一侧
                    return moreStrictCo == 1 ? left : right;
                } else if (lo.isOr()) {
                    // or操作，取更加不严格的一侧
                    return moreStrictCo == 2 ? left : right;
                }
            } else {
                // 左右两侧的值不一致，比较值的严格性
                int _moreStrictCo = 0; // 值不想等，则不可能为3了
                if (left_value.isNumber() && right_value.isNumber()) {
                    // 数字
                    if (left_co == CompareOperator.GT
                            || left_co == CompareOperator.GTE) {
                        // 更大的数字更严格
                        _moreStrictCo = left_value.asDouble(0) > right_value.asDouble(0) ?
                                1 : 2;
                    } else {
                        // 更小的数字更严格
                        _moreStrictCo = left_value.asDouble(0) < right_value.asDouble(0) ?
                                1 : 2;
                    }

                    if (lo.isAnd()) {
                        // and操作，取更加严格的一侧
                        return _moreStrictCo == 1 ? left : right;
                    } else if (lo.isOr()) {
                        // or操作，取更加不严格的一侧
                        return _moreStrictCo == 2 ? left : right;
                    }
                } else if (left_value.isGeometry() && right_value.isGeometry()) {
                    // 几何坐标，范围更小的更严格
                    if ((left_value.getValue() != null && right_value.getValue() != null) &&
                            ((Geometry) left_value.getValue()).contains(((Geometry) right_value.getValue()))) {
                        // left 包含了 right，所以right更加严格
                        _moreStrictCo = 2;
                    } else if ((left_value.getValue() != null && right_value.getValue() != null) &&
                            ((Geometry) right_value.getValue()).contains(((Geometry) left_value.getValue()))) {
                        // right 包含了 left，所以left更加严格
                        _moreStrictCo = 1;
                    }

                    if (moreStrictCo == 3 ||
                            moreStrictCo == _moreStrictCo) {
                        if (lo.isAnd()) {
                            // and操作，取更加严格的一侧
                            return _moreStrictCo == 1 ? left : right;
                        } else if (lo.isOr()) {
                            // or操作，取更加不严格的一侧
                            return _moreStrictCo == 2 ? left : right;
                        }
                    }
                }
            }
        }
        return null;
    }

}
