package com.easyray.idgeneratorapi.service;

import com.easyray.baseapi.provider.BaseLocalProvider;
import com.easyray.idgeneratorapi.entity.IdSequence;

/**
 * @Date: 20-2-11
 * @Author: wyy
 */
public interface IdSequenceLocalProvider extends BaseLocalProvider<IdSequence> {

    public IdSequence fetchByEntityName(String entityName);

    public IdSequence fetchById(long id);
}
