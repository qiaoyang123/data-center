package com.ggj.datacenter.entity;

import com.ggj.datacenter.entity.page.PageEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 参数实体类
 *
 * @author <a href="mailto:qiaoy@gegejia.com">qy</a>
 * @version 1.0 2018/4/12
 * @since 1.0
 */
public class CenterSqlParam extends PageEntity implements Serializable {

    private static final long serialVersionUID = 4642179706514299447L;

    private Long id;

    private Long sqlId;

    private String paramKey;

    private String paramTitle;

    private Integer isNecessary;

    private String paramOperator;

    private Integer paramStatus;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSqlId() {
        return sqlId;
    }

    public void setSqlId(Long sqlId) {
        this.sqlId = sqlId;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey == null ? null : paramKey.trim();
    }

    public String getParamTitle() {
        return paramTitle;
    }

    public void setParamTitle(String paramTitle) {
        this.paramTitle = paramTitle == null ? null : paramTitle.trim();
    }

    public Integer getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(Integer isNecessary) {
        this.isNecessary = isNecessary;
    }

    public String getParamOperator() {
        return paramOperator;
    }

    public void setParamOperator(String paramOperator) {
        this.paramOperator = paramOperator == null ? null : paramOperator.trim();
    }

    public Integer getParamStatus() {
        return paramStatus;
    }

    public void setParamStatus(Integer paramStatus) {
        this.paramStatus = paramStatus;
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