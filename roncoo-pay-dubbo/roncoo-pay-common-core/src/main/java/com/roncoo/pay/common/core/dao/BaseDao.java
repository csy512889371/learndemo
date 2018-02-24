/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列 ：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.common.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;

/**
 * @类功能说明： 数据访问层基础支撑接口.
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院）.
 * @版本：V1.0
 */
public interface BaseDao<T> {

    /**
     * 函数功能说明 ：单条插入数据. 修改者名字：Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param entity
     * @参数：@return
     * @return：int
     * @throws
     */
    int insert(T entity);

    /**
     * 函数功能说明 ： 批量插入数据. 修改者名字：Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param list
     * @参数：@return
     * @return：int
     * @throws
     */
    int insert(List<T> list);

    /**
     * 函数功能说明 ：根据id单条更新数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param entity
     * @参数：@return
     * @return：int
     * @throws
     */
    int update(T entity);

    /**
     * 函数功能说明 ： 根据id批量更新数据. 修改者名字：Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param list
     * @参数：@return
     * @return：int
     * @throws
     */
    int update(List<T> list);

    /**
     * 函数功能说明 ： 根据column批量更新数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param paramMap
     * @参数：@return
     * @return：int
     * @throws
     */
    int update(Map<String, Object> paramMap);

    /**
     * 函数功能说明 ： 根据id查询数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param id
     * @参数：@return
     * @return：T
     * @throws
     */
    T getById(String id);

    /**
     * 函数功能说明 ： 根据column查询数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param paramMap
     * @参数：@return
     * @return：T
     * @throws
     */
    public T getByColumn(Map<String, Object> paramMap);

    /**
     * 根据条件查询 listBy: <br/>
     * 
     * @param paramMap
     * @return 返回实体
     */
    public T getBy(Map<String, Object> paramMap);
    
    /**
     * 根据条件查询列表数据.
     */
    public List<T> listBy(Map<String, Object> paramMap);

    /**
     * 函数功能说明 ： 根据column查询列表数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param paramMap
     * @参数：@return
     * @return：List<T>
     * @throws
     */
    public List<T> listByColumn(Map<String, Object> paramMap);

    /**
     * 函数功能说明 ： 根据column查询记录数. 修改者名字： Along 修改日期： 2016-1-9 修改内容：
     * 
     * @参数：@param paramMap
     * @参数：@return
     * @return：Long
     * @throws
     */
    Long getCountByColumn(Map<String, Object> paramMap);

    /**
     * 函数功能说明 ： 根据id删除数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param id
     * @参数：@return
     * @return：int
     * @throws
     */
    int delete(String id);

    /**
     * 函数功能说明 ： 根据id批量删除数据. 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param list
     * @参数：@return
     * @return：int
     * @throws
     */
    int delete(List<T> list);

    /**
     * 函数功能说明 ： 根据column批量删除数据. 修改者名字：Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param paramMap
     * @参数：@return
     * @return：int
     * @throws
     */
    int delete(Map<String, Object> paramMap);

    /**
     * 函数功能说明 ： 分页查询数据 . 修改者名字： Along 修改日期： 2016-5-11 修改内容：
     * 
     * @参数：@param pageParam
     * @参数：@param paramMap
     * @参数：@return
     * @return：PageBean
     * @throws
     */
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap);

    public SqlSessionTemplate getSessionTemplate();

    public SqlSession getSqlSession();
}
