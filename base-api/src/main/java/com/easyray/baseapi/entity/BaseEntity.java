package com.easyray.baseapi.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.easyray.baseapi.constant.ColumnNameConstant;
import com.wyy.actable.annotation.Column;

import java.util.Date;

import static com.wyy.actable.constants.MySqlDataType.*;

/**
 * @Date: 20-1-26
 * @Author: wyy
 */

/**
 * @param <T> 主键类型
 */
public abstract class BaseEntity<T> extends PrimeKeyEntity<T> {
    @Column(name = ColumnNameConstant.user_id, type = BIGINT, length = 20, nullable = false)
    private long userId;
    @Column(name = ColumnNameConstant.full_name, type = VARCHAR, length = 20, nullable = false)
    private String fullName;

    @Column(name = ColumnNameConstant.create_date, type = DATETIME, nullable = false)
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;


    @Column(name = ColumnNameConstant.modified_date, type = DATETIME)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedDate;

    public BaseEntity() {
    }

    public BaseEntity(T id) {
        super(id);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public BaseEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public BaseEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public BaseEntity setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public BaseEntity setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }
}
