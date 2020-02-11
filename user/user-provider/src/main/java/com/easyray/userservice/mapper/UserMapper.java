package com.easyray.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyray.userapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author easyray
 * @since 2020-01-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
