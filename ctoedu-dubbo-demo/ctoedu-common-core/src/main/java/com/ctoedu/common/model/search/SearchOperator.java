package com.ctoedu.common.model.search;

import java.util.Arrays;

import com.ctoedu.common.model.search.exception.SearchException;
import org.apache.commons.lang3.StringUtils;


/**
 * 查询操作符
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public enum SearchOperator {
    eq("等于", "="), ne("不等于", "!="),
    gt("大于", ">"), gte("大于等于", ">="), lt("小于", "<"), lte("小于等于", "<="),
    prefixLike("前缀模糊匹配", "like"), prefixNotLike("前缀模糊不匹配", "not like"),
    suffixLike("后缀模糊匹配", "like"), suffixNotLike("后缀模糊不匹配", "not like"),
    like("模糊匹配", "like"), notLike("不匹配", "not like"),
    isNull("空", "is null"), isNotNull("非空", "is not null"),
    in("包含", "in"), notIn("不包含", "not in"), custom("自定义默认的", null);

    private final String info;
    private final String symbol;

    SearchOperator(final String info, String symbol) {
        this.info = info;
        this.symbol = symbol;
    }

    public String getInfo() {
        return info;
    }

    public String getSymbol() {
        return symbol;
    }

    public static String toStringAllOperator() {
        return Arrays.toString(SearchOperator.values());
    }

    /**
     * 操作符是否允许为空
     *
     * @param operator
     * @return
     */
    public static boolean isAllowBlankValue(final SearchOperator operator) {
        return operator == SearchOperator.isNotNull || operator == SearchOperator.isNull;
    }


    public static SearchOperator valueBySymbol(String symbol) throws SearchException {
        symbol = formatSymbol(symbol);
        for (SearchOperator operator : values()) {
            if (operator.getSymbol().equals(symbol)) {
                return operator;
            }
        }

        throw new SearchException("SearchOperator not method search operator symbol : " + symbol);
    }

    private static String formatSymbol(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return symbol;
        }
        return symbol.trim().toLowerCase().replace("  ", " ");
    }
}
