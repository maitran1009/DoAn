package com.pizza.model.output;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private int page;
    private int pageSize = 2;
    private long total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Integer> getTotalPage() {
        List<Integer> integers = new ArrayList<>();
        int length = (int) (this.total / this.pageSize);
        if (this.total % this.pageSize != 0) {
            length++;
        }

        for (int i = 1; i <= length; i++) {
            integers.add(i);
        }
        return integers;
    }

}
