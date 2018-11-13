package com.ggj.datacenter.entity.page;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页实体类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class PageEntity implements Serializable {

    private static final long serialVersionUID = -7496633217938075977L;
    private final Integer CURRENT_PAGE = 0;
    private final Integer PAGESIZE = 20;
    private Integer page;
    private Integer rows;
    private Integer total;
    private Integer start;
    private Map<String, String> orderMap;
    private String orders;

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Integer getStart() {
        if (this.rows != null) {
            return (this.page - 1) * this.rows;
        } else {
            return 0;
        }
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<String, String> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, String> orderMap) {
        this.orderMap = orderMap;
    }
}
