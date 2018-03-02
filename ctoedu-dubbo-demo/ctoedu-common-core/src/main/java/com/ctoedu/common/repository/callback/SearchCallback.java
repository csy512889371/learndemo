package com.ctoedu.common.repository.callback;

import javax.persistence.Query;

import com.ctoedu.common.model.search.Searchable;

public interface SearchCallback {

    public static final SearchCallback NONE = new NoneSearchCallback();
    public static final SearchCallback DEFAULT = new DefaultSearchCallback();

    /**
     * 动态拼HQL where、group by having
     *
     * @param ql
     * @param search
     */
    public void prepareQL(StringBuilder ql, Searchable search);

    public void prepareOrder(StringBuilder ql, Searchable search);

    /**
     * 根据search给query赋值及设置分页信息
     *
     * @param query
     * @param search
     */
    public void setValues(Query query, Searchable search);

    public void setPageable(Query query, Searchable search);

}
