package com.easyray.roleapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easyray.baseapi.entity.BaseEntity;
import com.easyray.roleapi.constant.RoleTypeConstant;
import com.wyy.actable.annotation.Column;
import com.wyy.actable.annotation.Table;

import static com.wyy.actable.constants.MySqlDataType.INT;
import static com.wyy.actable.constants.MySqlDataType.VARCHAR;

/**
 * @author wyy
 * @since 2020-02_08
 */
@TableName("sys_role")
@Table(name = "sys_role")
public class Role extends BaseEntity<Long> {

    @Column(name = "name", type = VARCHAR, length = 20, nullable = false)
    private String name;
    @Column(name = "type", type = INT, length = 1, nullable = false)
    private int type = RoleTypeConstant.GLOBAL_ROLE;

    public Role(Long id) {
        super(id);
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }
}
