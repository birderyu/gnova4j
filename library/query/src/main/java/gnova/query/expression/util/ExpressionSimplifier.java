package gnova.query.expression.util;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.query.expression.*;
import gnova.query.expression.util.ExpressionFactory;

public class ExpressionSimplifier {

    public static PredicateExpression simplify(@NotNull PredicateExpression predicateExpression) {

        if (predicateExpression.isInvariable()) {
            // 恒定的表达式，直接返回
            return predicateExpression;
        } else if (predicateExpression.isAlwaysTrue()) {
            // 恒为true的表达式，直接返回
            return new InvariableProxyExpression(predicateExpression, true);
        } else if (predicateExpression.isAlwaysFalse()) {
            // 恒为false的表达式，直接返回
            return new InvariableProxyExpression(predicateExpression, false);
        } else if (predicateExpression.isSimple()) {
            // 简单表达式，且不恒等，无法化简，直接返回
            return predicateExpression;
        } else if (predicateExpression.isNegative()) {
            // 否定表达式，可以化简
            return simplifyNegative(predicateExpression.asNegative());
        } else {
            // 复合表达式，可以化简
            return simplifyMultiple(predicateExpression.asMultiple());
        }

    }

    private static PredicateExpression simplifyNegative(@NotNull NegativeExpression ne) {
        if (ne.getPositive().isNegative()) {
            // 双重否定，化简
            return simplify(ne.getPositive().asNegative().getPositive());
        } else {
            // 化简其逆向的表达式，然后用化简的结果重构一个表达式
            return ExpressionFactory.buildNegative(simplify(ne.getPositive()));
        }
    }

    private static PredicateExpression simplifyMultiple(@NotNull MultipleExpression me) {

        PredicateExpression left = me.getLeft();
        PredicateExpression right = me.getRight();
        LogicalPredicate lo = me.getPredicate();

        // 恒等性判断
        if (left.isAlwaysTrue()) {
            // left是恒为true的，此时不可能是逻辑或操作
            if (lo.isAnd()) {
                // 逻辑与操作，且左侧为true，此时表达式的结果取决于右侧
                return simplify(right);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且左侧为true，此时表达式的结果取决于右侧
                return simplify(right);
            }
        } else if (left.isAlwaysFalse()) {
            // left是恒为false的，此时不可能是逻辑与操作
            if (lo.isOr()) {
                // 逻辑或操作，且左侧为false，此时表达式的结果取决于右侧
                return simplify(right);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且左侧为false，此时表达式的结果取决于右侧
                // 但是此时表达式的结果是右侧结果的逻辑非结果
                return ExpressionFactory.buildNegative(simplify(right));
            }
        }

        if (right.isAlwaysTrue()) {
            // right是恒为true的，此时不可能是逻辑或操作
            if (lo.isAnd()) {
                // 逻辑与操作，且右侧为true，此时表达式的结果取决于左侧
                return simplify(left);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且右侧为true，此时表达式的结果取决于左侧
                return simplify(left);
            }
        } else if (right.isAlwaysFalse()) {
            // right是恒为false的，此时不可能是逻辑与操作
            if (lo.isOr()) {
                // 逻辑或操作，且右侧为false，此时表达式的结果取决于左侧
                return simplify(left);
            } else if (lo.isXor()) {
                // 逻辑异或操作，且右侧为false，此时表达式的结果取决于左侧
                // 但是此时表达式的结果是左侧结果的逻辑非结果
                return ExpressionFactory.buildNegative(simplify(left));
            }
        }


        if (left.isSimple() && right.isSimple()) {

            return simplifyMultiple(me, left.asSimple(), lo, right.asSimple());

        } else {

            // 分别简化左右两侧
            PredicateExpression _left = simplify(left);
            PredicateExpression _right = simplify(right);
            if (_left.isSimple() && _right.isSimple()) {
                // 还可以继续简化
                return simplifyMultiple(null,
                        _left.asSimple(), lo, _right.asSimple());
            } else {
                // 不可以再简化了
                return ExpressionFactory.buildMultiple(_left, lo, _right);
            }
        }

    }

    private static PredicateExpression simplifyMultiple(PredicateExpression me,
                                                        @NotNull SimpleExpression left,
                                                        @NotNull LogicalPredicate lo,
                                                        @NotNull SimpleExpression right) {

        if (me == null) {
            me = ExpressionFactory.buildMultiple(left, lo, right);
        }

        // 左右两侧都是简单表达式，并且左右两侧都不会是恒true或恒false了
        // 这说明左右两侧都是键值表达式了
        if (left.placeholderSize() != 0 || right.placeholderSize() != 0) {
            // 左右两侧有占位符，不可能再简化了
            return me;
        }

        // 以下开始，左右两侧都不会有占位符了
        ValueExpression left_key = left.asSimple().getLeft();
        ValueExpression right_key = right.asSimple().getLeft();
        if (!left_key.asKey().equals(right_key.asKey())) {
            // 左右两侧的键不想等，不可能再简化了
            return me;
        }

        // 以下开始，左右两侧的键是相等的
        ValueExpression left_value = left.asSimple().getRight();
        ValuePredicate left_co = left.asSimple().getPredicate();
        ValueExpression right_value = right.asSimple().getRight();
        ValuePredicate right_co = right.asSimple().getPredicate();

        // 1. 判断等于、不等于、列表包含、列表不包含
        if (left_co == ValuePredicate.EQ &&
                right_co == ValuePredicate.EQ) {
            // 左右两侧都是等于操作
            PredicateExpression res = simplifyEquals(me, left,
                    (KeyExpression) left_key, left_value, right_value, lo);
            if (res != null) {
                return res;
            }
        } else if (left_co == ValuePredicate.NEQ &&
                right_co == ValuePredicate.NEQ) {
            // 左右两侧都是不等于操作
            // TODO
        } else if (left_co == ValuePredicate.EQ &&
                right_co == ValuePredicate.IN) {
            // 左侧是等于操作，右侧是列表包含操作
            // TODO
        }

        // 2. 判断互逆运算
        if (ValuePredicate.isInverse(left_co, right_co)) {
            // 若二者互为逆运算，下面判断二者的值是否相等
            PredicateExpression res = simplifyInverse(me,
                    left_value, right_value, lo);
            if (res != null) {
                return res;
            }
        }

        // 3. 判断比较符号的严格性
        int moreStrictCo = ValuePredicate.getMoreStrict(left_co, right_co);
        PredicateExpression res = simplifyMoreStrict(moreStrictCo,
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

    private static PredicateExpression simplifyEquals(@NotNull PredicateExpression me,
                                                      @NotNull SimpleExpression se,
                                                      @NotNull KeyExpression key,
                                                      @NotNull ValueExpression lv,
                                                      @NotNull ValueExpression rv,
                                                      @NotNull LogicalPredicate lo) {
        // 左右两侧都是等于操作，下面判断二者的值是否相等
        if (equals(lv, rv)) {
            // 左右两侧的值完全一致
            if (lo.isXor()) {
                // 逻辑异或操作，此时表达式是恒等的，如
                // a = null xor a = null
                // a = 3 xor a = 3
                return new InvariableProxyExpression(me, true);
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
                return new InvariableProxyExpression(me, false);
            } else if (lo.isOr()) {
                // 逻辑或操作，此时表达式可以合并成一个列表表达式，如
                // a = null or a = 1 -> a in (null, 1)
                // a = null or a = 1 -> a in (null, 1)
                return ExpressionFactory.buildSimple(key,
                        ValuePredicate.IN,
                        ExpressionFactory.buildList(new ValueExpression[] {lv, rv}));
            }
        }
        return null;
    }

    private static PredicateExpression simplifyInverse(@NotNull PredicateExpression me,
                                                       @NotNull ValueExpression lv,
                                                       @NotNull ValueExpression rv,
                                                       @NotNull LogicalPredicate lo) {
        if (equals(lv, rv)) {
            // 左右两侧的值完全一致
            if (lo.isAnd()) {
                // and，恒不等，如：
                // age > 20 and age <= 20 -> false
                // age = 10 and age != 10 -> false
                return new InvariableProxyExpression(me, false);
            } else if (lo.isOr()) {
                // or，恒等，如：
                // age > 20 or age <= 20 -> true
                // age = 10 or age != 10 -> true
                return new InvariableProxyExpression(me, true);
            } else if (lo.isXor()) {
                // xor，恒等，如：
                // age > 20 xor age <= 20 -> true
                // age = 10 xor age != 10 -> true
                return new InvariableProxyExpression(me, true);
            }
        }
        return null;
    }

    private static PredicateExpression simplifyMoreStrict(int moreStrictCo,
                                                          @NotNull SimpleExpression left,
                                                          @NotNull LogicalPredicate lo,
                                                          @NotNull SimpleExpression right,
                                                          @NotNull ValueExpression left_value,
                                                          @NotNull ValuePredicate left_co,
                                                          @NotNull ValueExpression right_value,
                                                          @NotNull ValuePredicate right_co) {
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
                    if (left_co == ValuePredicate.GT
                            || left_co == ValuePredicate.GTE) {
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
