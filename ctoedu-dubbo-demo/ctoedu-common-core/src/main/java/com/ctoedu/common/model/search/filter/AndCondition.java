package com.ctoedu.common.model.search.filter;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * and 条件
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public class AndCondition implements SearchFilter,Serializable {

	private static final long serialVersionUID = 1L;
	private List<SearchFilter> andFilters = Lists.newArrayList();

    AndCondition() {
    }

    public AndCondition add(SearchFilter filter) {
        this.andFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getAndFilters() {
        return andFilters;
    }

    @Override
    public String toString() {
        return "AndCondition{" + andFilters + '}';
    }
}
