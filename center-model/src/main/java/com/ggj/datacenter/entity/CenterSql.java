package com.ggj.datacenter.entity;

import com.ggj.datacenter.entity.page.PageEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * sql实体类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class CenterSql extends PageEntity implements Serializable {

    private static final long serialVersionUID = 4642179706514299457L;

    private Long id;

    private String name;

    private String sql;

    private Integer status;

    private Integer type;

    private Integer cacheSql;

    private Long cacheSqlTime;

    private Integer cacheResult;

    private Long cacheResultTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql == null ? null : sql.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCacheSql() {
        return cacheSql;
    }

    public void setCacheSql(Integer cacheSql) {
        this.cacheSql = cacheSql;
    }

    public Long getCacheSqlTime() {
        return cacheSqlTime;
    }

    public void setCacheSqlTime(Long cacheSqlTime) {
        this.cacheSqlTime = cacheSqlTime;
    }

    public Integer getCacheResult() {
        return cacheResult;
    }

    public void setCacheResult(Integer cacheResult) {
        this.cacheResult = cacheResult;
    }

    public Long getCacheResultTime() {
        return cacheResultTime;
    }

    public void setCacheResultTime(Long cacheResultTime) {
        this.cacheResultTime = cacheResultTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}