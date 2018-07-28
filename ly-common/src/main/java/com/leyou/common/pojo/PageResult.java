package com.leyou.common.pojo;

import java.util.List;

/**
 * 分页实体
 *
 * @author: cooFive
 * @CreateDate: 2018/7/21 15:41
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class PageResult<T> {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 当前页数据
     */
    private List<T> items;

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total + "\n" +
                ", totalPage=" + totalPage + "\n" +
                ", items=" + items + "\n" +
                '}';
    }
}
