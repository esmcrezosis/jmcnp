package com.esmc.mcnp.commons.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pagination template
 */
public class PageBean<T> {

    private List<T> pageData = new ArrayList<>();
    private Integer pageSize = Integer.valueOf(10);
    private Integer pageNo = Integer.valueOf(1);
    private Long totalCount = 0L;
    private String query;

    public PageBean(List<T> pageData, Long totalCount) {
        this.pageData = pageData;
        this.totalCount = totalCount;
    }

    public PageBean() {

    }

    public List<T> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNo() {
        return this.pageNo - 1;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}