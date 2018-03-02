package com.ctoedu.common.repository;

/**
 * <p>实体实现该接口表示想要逻辑删除
 * 为了简化开发 约定删除标识列名为deleted，如果想自定义删除的标识列名：

 */
public interface LogicDeleteable {

    public Short getDeleted();

    public void setDeleted(Short deleted);

    /**
     * 标识为已删除
     */
    public void markDeleted();

}
