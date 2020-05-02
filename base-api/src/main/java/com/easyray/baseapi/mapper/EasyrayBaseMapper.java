package com.easyray.baseapi.mapper;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.easyray.baseapi.constant.ColumnNameConstant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 20-2-13
 * @Author: wyy
 */
public interface EasyrayBaseMapper<T> extends BaseMapper<T> {

    /**
     * 使用sql注入，{@link com.easyray.baseapi.sqlinject.FilterFindByQuery}
     *
     * @param queryWrapper
     * @param groupId
     * @param userId
     * @return
     */
    List<T> filterFindByQuery(@Param(Constants.WRAPPER) AbstractWrapper queryWrapper, long groupId, long userId);

    /**
     * sql注入 {@link com.easyray.baseapi.sqlinject.FetchOneByQueryAndGroupId}
     *
     * @param queryWrapper
     * @param groupId
     * @return
     */
    T fetchOneByQueryAndGroupId(@Param(Constants.WRAPPER) AbstractWrapper queryWrapper, Long groupId);


    default IPage<T> fetchByQueryAndGroupId(IPage<T> page, @Param(Constants.WRAPPER) AbstractWrapper queryWrapper, Long groupId) {

        assert page != null;

        if (groupId != null) {
            queryWrapper.eq(ColumnNameConstant.group_id, groupId);
        }
        return fetchByQuery(page, queryWrapper);
    }

    default List<T> fetchByQueryAndGroupId(@Param(Constants.WRAPPER) AbstractWrapper queryWrapper, Long groupId) {
        if (groupId != null) {
            queryWrapper.eq(ColumnNameConstant.group_id, groupId);
        }
        return fetchByQuery(queryWrapper);
    }

    /**
     * sql注入 {@link com.easyray.baseapi.sqlinject.FetchByQuery}
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<T> fetchByQuery(IPage<T> page, @Param(Constants.WRAPPER) AbstractWrapper queryWrapper);

    /**
     * sql注入 {@link com.easyray.baseapi.sqlinject.FetchByQuery}
     *
     * @param queryWrapper
     * @return
     */
    List<T> fetchByQuery(@Param(Constants.WRAPPER) AbstractWrapper queryWrapper);

}
