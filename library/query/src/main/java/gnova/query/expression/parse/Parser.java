package gnova.query.expression.parse;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.FactoryFinder;
import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.*;
import gnova.query.expression.CompareOperator;
import gnova.query.expression.Expression;
import gnova.query.expression.LogicalOperator;
import gnova.query.expression.ValueExpression;
import gnova.query.expression.value.*;

import java.util.*;

public class Parser {

    @NotNull
    public static Expression parse(@Checked String whereClause)
            throws ParseException {

        if (whereClause == null || whereClause.isEmpty()) {
            throw new ParseException("字符串为空", whereClause, -1);
        }
        StringStream stream = preParse(whereClause,
                0, whereClause.length());
        if (stream.isEmpty()) {
            throw new ParseException("字符串中没有有效的信息", whereClause, -1);
        }

        return parse(stream);

    }

    @NotNull
    private static Expression parse(StringStream stream)
            throws ParseException {

        ParserStack stack = new ParserStack(stream.getValue());

        // 处理列表值
        Collection<ValueExpression> values = null;

        int valueStartCursor = stream.getBeginIndex(); // 值表达式开始的位置
        boolean valueStarted = true;
        while (stream.hasCurrent()) {

            //
            BracketScope bracketScope = stream.inBracket();
            if (bracketScope != null) {
                // 位于括号之内
                if (bracketScope.isParenthesis()) {
                    // 小括号
                    Expression exp = parseParenthesis(stream.subString(
                            bracketScope.getBeginIndex() + 1,
                            bracketScope.getEndIndex() - 1));
                    if (exp == null) {
                        throw new ParseException("解析失败，括号内部解析失败",
                                stream.getValue(), stream.getCursor());
                    } else if (exp.isValue()) {
                        // 解析出一个值表达式
                        if (values != null && !values.isEmpty()) {
                            values.add(exp.asValue());
                        } else {
                            stack.pushValue(exp.asValue(), stream.getCursor());
                        }
                    } else {
                        // 解析出一个逻辑表达式
                        stack.push(exp.asLogical(), stream.getCursor());
                    }
                } else {
                    // 方括号，解析成一个几何区域值
                    if (bracketScope.getParent() != null &&
                            !bracketScope.getParent().isParenthesis()) {
                        // 该方括号有一个父括号，且该父括号的也为方括号
                        throw new ParseException("解析失败",
                                stream.getValue(), stream.getCursor());
                    }
                    GeometryExpression value = parseGeometry(stream.subString(
                            bracketScope.getBeginIndex() + 1,
                            bracketScope.getEndIndex() - 1));
                    if (values != null && !values.isEmpty()) {
                        values.add(value);
                    } else {
                        stack.pushValue(value, stream.getCursor());
                    }
                }
                // 将光标移动到括号之外
                stream.nextTo(bracketScope.getEndIndex());

                // 重新开始解析一个值
                valueStarted = false;
                continue;
            }

            QuotationScope quotationScope = stream.inQuotation();
            if (quotationScope != null) {
                // 引号解析成字符串
                StringExpression value = parseString(stream.subString(
                        quotationScope.getBeginIndex() + 1,
                        quotationScope.getEndIndex() - 1));
                if (values != null && !values.isEmpty()) {
                    values.add(value);
                } else {
                    stack.pushValue(value, stream.getCursor());
                }
                // 将光标移动到引号之外
                stream.nextTo(quotationScope.getEndIndex());

                // 重新开始解析一个值
                valueStarted = false;
                continue;
            }

            // 不在括号内，也不在引号内
            // 验证是否是逻辑操作符
            Iterator<LogicalOperator> loCatchOrder = LogicalOperator.getMatchOrder();
            boolean loMatched = false;
            while (loCatchOrder.hasNext()) {
                LogicalOperator lo = loCatchOrder.next();
                if (stream.matches(lo.getSymbol(), lo.getPrefix(), lo.getSuffix())) {
                    // 匹配成功当前的逻辑操作符
                    loMatched = true;
                    stack.push(lo, stream.getCursor());

                    // 将光标移动到逻辑操作符之外
                    stream.nextN(lo.getSymbol().length());

                    // 重新开始解析一个值
                    valueStarted = false;
                    break;
                }
            }
            if (loMatched) {
                // 重新开始解析一个值
                valueStarted = false;
                continue;
            }

            // 验证是否是比较操作符
            Iterator<CompareOperator> coCatchOrder = CompareOperator.getMatchOrder();
            boolean coMatched = false;
            while (coCatchOrder.hasNext()) {
                CompareOperator co = coCatchOrder.next();
                if (stream.matches(co.getSymbol(), co.getPrefix(), co.getSuffix())) {
                    // 匹配成功当前的逻辑操作符
                    coMatched = true;
                    stack.push(co, stream.getCursor());

                    // 将光标移动到逻辑操作符之外
                    stream.nextN(co.getSymbol().length());

                    // 重新开始解析一个值
                    valueStarted = false;
                    break;
                }
            }
            if (coMatched) {
                // 重新开始解析一个值
                valueStarted = false;
                continue;
            }

            // 验证是否是一些比较特殊的值
            // 其他值
            char c = stream.getCurrent();
            if (c == '!') {
                // 否定表达式
                stack.pushNon();

                // 重新开始解析一个值
                valueStarted = false;
            } else if (c == ' ') {
                // 空格，开始解析一个值
                if (valueStarted) {
                    ValueExpression ve = parseValue(stream.subString(
                            valueStartCursor, stream.getCursor()));
                    if (values != null && !values.isEmpty()) {
                        values.add(ve);
                    } else {
                        stack.pushValue(ve, stream.getCursor());
                    }

                    // 重新开始解析一个值
                    valueStarted = false;
                }
            } else if (c == ',') {
                // 逗号，开始解析一个列表值
                if (valueStarted) {
                    Expression ve = parse(stream.subString(
                            valueStartCursor, stream.getCursor()));
                    if (!ve.isValue()) {
                        throw new ParseException("逗号前必须是一个值表达式",
                                stream.getValue(), stream.getCursor());
                    }
                    if (values == null) {
                        values = new ArrayList<>();
                    }
                    values.add(ve.asValue());

                    // 重新开始解析一个值
                    valueStarted = false;
                } else {
                    // 说明前一个值应该是列表值的一部分
                    ValueExpression ve = stack.pop();
                    if (ve != null) {
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        values.add(ve);
                    }
                }
            } else {
                if (!valueStarted) {
                    valueStarted = true;
                    valueStartCursor = stream.getCursor();
                }
            }

            stream.next();
        }

        // 还有未解析的值
        ValueExpression ve = null;
        if (valueStarted) {
            ve = parseValue(stream.subString(
                    valueStartCursor, stream.getEndIndex()));
            if (values != null && !values.isEmpty()) {
                values.add(ve);
                ve = new ListExpression(values);
            }
        } else {
            if (values != null && !values.isEmpty()) {
                ve = new ListExpression(values);
            }
        }

        if (ve != null) {
            stack.pushValue(ve, stream.getCursor());
        }

        return stack.get();

    }

    /**
     * 解析小括号
     * @param stream
     * @return
     */
    private static Expression parseParenthesis(StringStream stream)
            throws ParseException {
        stream = stream.trim();
        if (stream.isEmpty()) {
            // 空括号
            return ListExpression.EMPTY;
        }
        Expression exp = parse(stream);
        if (exp.isValue() && !exp.asValue().isList()) {
            // 处理列表中只包含一个值的情况
            return new ListExpression(new ValueExpression[] {exp.asValue()});
        }
        return exp;
    }

    private static ValueExpression parseValue(StringStream stream)
            throws ParseException {

        // 注意，字符串、列表、几何区域已经在其他地方处理了
        // 这里的结果只可能是占位符、空值、布尔值、数字值和键值

        //stream = stream.trim();
        if (stream.isEmpty()) {
            throw new ParseException("值表达式解析失败，值不允许为空",
                    stream.getValue(), stream.getCursor());
        }

        if (stream.getCurrent() == '?' && stream.size() == 1) {
            return PlaceholderExpression.PLACEHOLDER;
        } else if (stream.matches("null", null, null) &&
                stream.size() == 4) {
            return NullExpression.NULL;
        } else if (stream.matches("true", null, null) &&
                stream.size() == 4) {
            return BooleanExpression.TRUE;
        } else if (stream.matches("false", null, null) &&
                stream.size() == 5) {
            return BooleanExpression.FALSE;
        } else {
            String v = stream.toString();
            char c = v.charAt(0);
            if (charIsNumber(c) || c == '-') {
                // 可能是数字，试着转换为数字
                try {
                    long lv = Long.valueOf(v);
                    if (lv <= MAX_INT_32 && lv >= MIN_INT_32) {
                        return new Int32Expression((int) lv);
                    } else {
                        return new Int64Expression(lv);
                    }
                } catch (NumberFormatException e) {
                    try {
                        double dv = Double.valueOf(v);
                        return new DoubleExpression(dv);
                    } catch (NumberFormatException ex) {
                        // do nothing
                    }
                }
                throw new ParseException("键值只能够以字母和下划线作为开头",
                        stream.getValue(), stream.getCursor());
            } else {
                // 键值
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_') {
                    return new KeyExpression(v);
                } else {
                    throw new ParseException("键值只能够以字母和下划线作为开头",
                            stream.getValue(), stream.getCursor());
                }

            }
        }

    }

    /**
     * 解析括号
     * @param stream
     * @return
     */
    @NotNull
    private static GeometryExpression parseGeometry(StringStream stream)
            throws ParseException {

        if (stream.isEmpty()) {
            return GeometryExpression.EMPTY;
        }

        Collection<Geometry> geometries = null;
        int type = 0; // 1-LineString, 2-Polygon
        while (stream.hasCurrent()) {
            BracketScope bracketScope = stream.inBracket();
            if (bracketScope != null) {
                if (bracketScope.isParenthesis()) {
                    // 解析成LineString
                    LinearRing linearRing = parseLinearRing(stream.subString(
                            bracketScope.getBeginIndex() + 1,
                            bracketScope.getEndIndex() - 1));
                    if (type == 0) {
                        type = 1;
                    } else if (type == 2) {
                        throw new ParseException("几何区域值解析失败，不允许嵌套不同的几何类型",
                                stream.getValue(), stream.getCursor());
                    }
                    if (geometries == null) {
                        geometries = new ArrayList<>();
                    }
                    geometries.add(linearRing);
                } else {
                    // 解析成Polygon
                    Polygon polygon = parsePolygon(stream.subString(
                            bracketScope.getBeginIndex() + 1,
                            bracketScope.getEndIndex() - 1));
                    if (type == 0) {
                        type = 2;
                    } else if (type == 1) {
                        throw new ParseException("几何区域值解析失败，不允许嵌套不同的几何类型",
                                stream.getValue(), stream.getCursor());
                    }
                    if (geometries == null) {
                        geometries = new ArrayList<>();
                    }
                    geometries.add(polygon);
                }
                stream.nextTo(bracketScope.getEndIndex());
                continue;
            }
            stream.next();
        }

        if (geometries == null || geometries.isEmpty()) {
            return GeometryExpression.EMPTY;
        } else {

            if (type == 1) {

                LinearRing shell = null;
                Collection<LinearRing> holes = null;
                for (Geometry geometry : geometries) {
                    if (shell == null) {
                        shell = (LinearRing) geometry;
                    } else {
                        if (holes == null) {
                            holes = new ArrayList<>();
                        }
                        holes.add((LinearRing) geometry);
                    }
                }
                LinearRing[] _holes = (holes == null || holes.isEmpty()) ?
                        null : holes.toArray(new LinearRing[holes.size()]);
                Polygon polygon = GEOMETRY_FACTORY.createPolygon(shell, _holes);
                return new GeometryExpression(polygon);

            } else if (type == 2) {

                Polygon[] polygons = new Polygon[geometries.size()];
                int index = 0;
                for (Geometry geometry : geometries) {
                    polygons[index++] = (Polygon) geometry;
                }
                MultiPolygon multiPolygon = GEOMETRY_FACTORY.createMultiPolygon(polygons);
                return new GeometryExpression(multiPolygon);

            }
        }

        throw new ParseException("几何区域值解析失败，未知的错误",
                stream.getValue(), stream.getCursor());
    }

    @NotNull
    private static StringExpression parseString(StringStream stream) {

        if (stream.isEmpty()) {
            return StringExpression.EMPTY;
        }
        return new StringExpression(stream.toString());
    }

    /**
     * 预解析
     *
     * @param whereClause
     * @param beginIndex
     * @param endIndex
     * @return
     */
    @NotNull
    private static StringStream preParse(@NotNull String whereClause,
                                         @Checked int beginIndex,
                                         @Checked int endIndex)
            throws ParseException {

        StringStream s = new StringStream(whereClause,
                beginIndex, endIndex, null, null);
        if (s.isEmpty()) {
            return s;
        }

        Deque<QuotationScope> quotationScopes = null; // 引号的范围
        Deque<BracketScope> bracketScopes = null; // 括号的范围
        boolean inQuotation = false; // 是否位于引号中
        int leftDoubleQuotationCursor = -1; // 上一个左双引号的下标
        int leftSingleQuotationCursor = -1; // 上一个左单引号的下标
        Stack<BracketScope> bracketScopeStack = null; // 括号栈

        while (s.hasCurrent()) {
            char c = s.getCurrent();
            if (inQuotation) {
                // 位于引号中
                if (leftSingleQuotationCursor != -1 &&
                        c == '\'') {
                    if (s.hasPrevious() &&
                            s.getPreviousN(1) == '\\') {
                        // 转义符，直接忽略
                        // do nothing
                    } else {
                        // 非转义符，引号结束
                        // 将引号的首尾下标存入数组
                        if (quotationScopes == null) {
                            quotationScopes = new LinkedList<>();
                        }
                        QuotationScope quotationScope = new QuotationScope(
                                leftSingleQuotationCursor,
                                s.getCursor() + 1);
                        quotationScopes.add(quotationScope);
                        inQuotation = false;
                        leftSingleQuotationCursor = -1;
                    }
                } else if (leftDoubleQuotationCursor != -1 &&
                        c == '"') {
                    if (s.hasPrevious() &&
                            s.getPreviousN(1) == '\\') {
                        // 转义符，直接忽略
                        // do nothing
                    } else {
                        // 非转义符，引号结束
                        // 将引号的首尾下标存入数组
                        if (quotationScopes == null) {
                            quotationScopes = new LinkedList<>();
                        }
                        QuotationScope quotationScope = new QuotationScope(
                                leftDoubleQuotationCursor,
                                s.getCursor() + 1);
                        quotationScopes.add(quotationScope);
                        inQuotation = false;
                        leftDoubleQuotationCursor = -1;
                    }
                }
            } else {
                // 不位于引号中
                if (c == '\'') {
                    if (s.hasPrevious() &&
                            s.getPreviousN(1) == '\\') {
                        // 转义符，直接忽略
                        // do nothing
                    } else if (leftDoubleQuotationCursor != -1 ) {
                        // 虽然遇到了一个单引号，但是在双引号之中，如："I'm good."
                        // do nothing
                    } else {
                        leftSingleQuotationCursor = s.getCursor();
                        inQuotation = true;
                    }
                } else if (c == '"') {
                    if (s.hasPrevious() &&
                            s.getPreviousN(1) == '\\') {
                        // 转义符，直接忽略
                        // do nothing
                    } else if (leftSingleQuotationCursor != -1 ) {
                        // 虽然遇到了一个双引号，但是在单引号之中，如：'He is a "GOOD" man.'"
                        // do nothing
                    } else {
                        leftDoubleQuotationCursor = s.getCursor();
                        inQuotation = true;
                    }
                } else if (c == '(') {
                    // 左圆括号
                    if (bracketScopeStack == null) {
                        bracketScopeStack = new Stack<>();
                    }
                    if (bracketScopeStack.isEmpty()) {
                        // 无嵌套的括号
                        BracketScope bracketScope = new BracketScope(null,
                                true, s.getCursor());
                        bracketScopeStack.push(bracketScope);
                    } else {
                        // 嵌套的括号
                        BracketScope parent = bracketScopeStack.peek();
                        BracketScope bracketScope = new BracketScope(parent,
                                true, s.getCursor());
                        parent.addChild(bracketScope);
                        bracketScopeStack.push(bracketScope);
                    }

                } else if (c == ')') {
                    // 右圆括号
                    if (bracketScopeStack == null || bracketScopeStack.isEmpty()) {
                        throw new ParseException("右圆括号不允许单独出现", whereClause, s.getCursor());
                    }
                    BracketScope bracketScope = bracketScopeStack.pop();
                    if (!bracketScope.isParenthesis()) {
                        throw new ParseException("圆括号与方括号不允许交叉嵌套", whereClause, s.getCursor());
                    }
                    bracketScope.setEndIndex(s.getCursor() + 1);
                    if (bracketScopes == null) {
                        bracketScopes = new LinkedList<>();
                    }
                    bracketScopes.offer(bracketScope);
                } else if (c == '[') {
                    // 左方括号
                    if (bracketScopeStack == null) {
                        bracketScopeStack = new Stack<>();
                    }
                    if (bracketScopeStack.isEmpty()) {
                        // 无嵌套的括号
                        BracketScope bracketScope = new BracketScope(null,
                                false, s.getCursor());
                        bracketScopeStack.push(bracketScope);
                    } else {
                        // 嵌套的括号
                        BracketScope parent = bracketScopeStack.peek();
                        BracketScope bracketScope = new BracketScope(parent,
                                false, s.getCursor());
                        bracketScopeStack.push(bracketScope);
                    }

                } else if (c == ']') {
                    // 右方括号
                    if (bracketScopeStack == null || bracketScopeStack.isEmpty()) {
                        throw new ParseException("右方括号不允许单独出现", whereClause, s.getCursor());
                    }
                    BracketScope bracketScope = bracketScopeStack.pop();
                    if (bracketScope.isParenthesis()) {
                        throw new ParseException("圆括号与方括号不允许交叉嵌套", whereClause, s.getCursor());
                    }
                    bracketScope.setEndIndex(s.getCursor() + 1);
                    if (bracketScopes == null) {
                        bracketScopes = new LinkedList<>();
                    }
                    bracketScopes.offer(bracketScope);
                }
            }
            s.next();
        }

        if (bracketScopeStack != null && !bracketScopeStack.isEmpty()) {
            throw new ParseException("括号必须成对出现", whereClause, s.getCursor());
        }

        // 删除字符串两侧的空格和无用的括号
        StringStream stream = new StringStream(whereClause, beginIndex, endIndex,
                quotationScopes, bracketScopes);
        return trimAndRemoveSideBracket(stream);
    }

    /**
     * 去除无效的空格和引号
     *
     * 如 (a = "ABC" and b = 3)
     * 这个括号就是无效的
     *
     * @param stream
     * @return
     */
    private static StringStream trimAndRemoveSideBracket(@NotNull StringStream stream) {

        Collection<BracketScope> bracketScopes = stream.getBracketScopes();
        if (bracketScopes == null || bracketScopes.isEmpty()) {
            return stream.trim();
        }

        // 将字符串两边的空格和两侧无效的括号移除
        while (true) {
            stream = stream.trim();
            Iterator<BracketScope> iterator = bracketScopes.iterator();
            while (iterator.hasNext()) {
                BracketScope bracketScope = iterator.next();
                // 括号的范围已经完全包含了当前字符串的有效范围
                // 该括号是无效的
                if (bracketScope.containsTo(stream.getBeginIndex(), stream.getEndIndex())) {
                    stream = stream.subString(bracketScope.getBeginIndex() + 1,
                            stream.getEndIndex() - 1);
                    iterator.remove();
                    // 移除之后，需要让循环继续
                    break;
                } else {
                    return stream;
                }
            }
        }
    }

    /**
     * 移除已经失效的符号
     *
     * 如 a = "ABC" and b = 3
     * 若此时已经解析到了引号以后的位置，则认为引号已经失效了
     *
     * @param beginIndex
     * @param endIndex
     * @param scopes
     */
    private static void removeInvalidScope(int beginIndex, int endIndex,
                                           @NotNull Deque<? extends StringScope> scopes) {
        StringScope scope = scopes.peekFirst();
        while (scope != null) {
            if (scope.getEndIndex() <= beginIndex) {
                scopes.removeFirst();
            } else {
                break;
            }
        }
        scope = scopes.peekLast();
        while (scope != null) {
            if (scope.getBeginIndex() >= endIndex) {
                scopes.removeLast();
            } else {
                break;
            }
        }
    }

    private static boolean charIsNumber(char c) {
        for (int i = 0; i < number_char.length; i++) {
            if (c == number_char[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否不是一个键值
     *
     * 键必须以下划线和字母大头
     *
     * @param c
     * @return
     */
    private static boolean charIsNotKey(char c) {
        return charIsNumber(c);
    }

    private static LinearRing parseLinearRing(StringStream stream)
            throws ParseException {

        Collection<Coordinate> coordinates = new ArrayList<>();

        int beginIndex = stream.getBeginIndex();
        boolean begin = false;
        Queue<Double> coord = new LinkedList<>();
        while (stream.hasCurrent()) {
            char c = stream.getCurrent();
            if (c == ' ') {
                if (begin) {
                    Double dv = Double.valueOf(stream.subString(beginIndex,
                            stream.getCursor()).toString());
                    coord.add(dv);
                    begin = false;
                }
            } else if (c == ',') {
                if (begin) {
                    Double dv = Double.valueOf(stream.subString(beginIndex,
                            stream.getCursor()).toString());
                    coord.add(dv);
                }
                Coordinate coordinate = null;
                if (coord.size() == 2) {
                    coordinate = new Coordinate(coord.poll(), coord.poll());
                } else if (coord.size() == 3) {
                    coordinate = new Coordinate(coord.poll(), coord.poll(),
                            coord.poll());
                } else if (coord.size() == 4) {
                    coordinate = new Coordinate(coord.poll(), coord.poll(),
                            coord.poll(), coord.poll());
                } else {
                    throw new ParseException("坐标解析失败，不合理的坐标值数量（" + coord.size() + "）",
                            stream.getValue(), stream.getCursor());
                }
                coordinates.add(coordinate);
                begin = false;
            } else {
                if (!begin) {
                    beginIndex = stream.getCursor();
                    begin = true;
                }
            }
            stream.next();
        }

        if (begin) {
            Double dv = Double.valueOf(stream.subString(beginIndex,
                    stream.getCursor()).toString());
            coord.add(dv);
        }
        Coordinate coordinate = null;
        if (coord.size() == 2) {
            coordinate = new Coordinate(coord.poll(), coord.poll());
        } else if (coord.size() == 3) {
            coordinate = new Coordinate(coord.poll(), coord.poll(),
                    coord.poll());
        } else if (coord.size() == 4) {
            coordinate = new Coordinate(coord.poll(), coord.poll(),
                    coord.poll(), coord.poll());
        } else {
            throw new ParseException("坐标解析失败，不合理的坐标值数量（" + coord.size() + "）",
                    stream.getValue(), stream.getCursor());
        }
        coordinates.add(coordinate);

        if (coordinates.isEmpty()) {
            throw new ParseException("坐标为空",
                    stream.getValue(), stream.getCursor());
        }

        return GEOMETRY_FACTORY.createLinearRing(coordinates);

    }

    private static Polygon parsePolygon(StringStream stream)
            throws ParseException {

        Collection<LinearRing> linearRings = new ArrayList<>();
        while (stream.hasCurrent()) {
            BracketScope bracketScope = stream.inBracket();
            if (bracketScope != null) {
                if (!bracketScope.isParenthesis()) {
                    throw new ParseException("坐标解析失败",
                            stream.getValue(), stream.getCursor());
                }
                linearRings.add(parseLinearRing(stream.subString(
                        bracketScope.getBeginIndex() + 1,
                        bracketScope.getEndIndex() - 1)));
                stream.nextTo(bracketScope.getEndIndex());
                continue;
            }
            stream.next();
        }

        if (linearRings.isEmpty()) {
            throw new ParseException("坐标环为空",
                    stream.getValue(), stream.getCursor());
        }
        LinearRing shell = null;
        Collection<LinearRing> holes = null;
        for (LinearRing linearRing : linearRings) {
            if (shell == null) {
                shell = linearRing;
            } else {
                if (holes == null) {
                    holes = new ArrayList<>();
                }
                holes.add(linearRing);
            }
        }
        LinearRing[] _holes = (holes == null || holes.isEmpty()) ?
                null : holes.toArray(new LinearRing[holes.size()]);
        return GEOMETRY_FACTORY.createPolygon(shell, _holes);
    }

    private static final char[] number_char = new char[] {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
    };

    private static final char[] letter_char = new char[] {
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
    };

    private static final long MAX_INT_32 = Integer.MAX_VALUE;
    private static final long MIN_INT_32 = Integer.MIN_VALUE;

    private static final GeometryFactory GEOMETRY_FACTORY
            = FactoryFinder.getDefaultGeometryFactory();
}
