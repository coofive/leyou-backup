package com.leyou.search.pojo;

/**
 * 搜索字段实体类
 *
 * @author: cooFive
 * @CreateDate: 2018/7/28 19:53
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class SearchRequest {
    /**
     * 每页大小，不从页面接收，而是固定大小
     */
    private static final Integer DEFAULT_SIZE = 20;
    /**
     * 默认页
     */
    private static final Integer DEFAULT_PAGE = 1;

    /**
     * 搜索条件
     */
    private String key;

    /**
     * 当前页
     */
    private Integer page;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        // 不能传入负数
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}
