/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/details/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.roncoo.pay.common.core.page;

import java.io.Serializable;

/**
 * @类功能说明： 分页参数传递工具类 .
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：广州领课网络科技有限公司（龙果学院）
 * @作者：Along
 * @创建时间：2016-5-11 上午11:14:10
 * @版本：V1.0
 */
public class PageParam implements Serializable {

    /**
   * 
   */
    private static final long serialVersionUID = 6297178964005032338L;

    /**
     * 默认为第一页.
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页记录数(15).
     */
    public static final int DEFAULT_NUM_PER_PAGE = 15;

    /**
     * 最大每页记录数(100).
     */
    public static final int MAX_PAGE_SIZE = 10000;

    private int pageNum = DEFAULT_PAGE_NUM; // 当前页数

    private int numPerPage; // 每页记录数

    /**
     * 默认构造函数
     */
    public PageParam(){}

    /**
     * 带参数的构造函数
     * @param pageNum
     * @param numPerPage
     */
    public PageParam(int pageNum , int numPerPage){
    	this.pageNum = pageNum;
    	this.numPerPage = numPerPage;
    }

    /** 当前页数 */
    public int getPageNum() {
        return pageNum;
    }

    /** 当前页数 */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /** 每页记录数 */
    public int getNumPerPage() {
        return numPerPage > 0 ? numPerPage : DEFAULT_NUM_PER_PAGE;
    }

    /** 每页记录数 */
    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

}
