package com.easyray.extension.autofill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.easyray.baseapi.constant.FieldNameConstant;
import com.easyray.idgeneratorapi.provider.IdService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Date: 20-4-29
 * @Author: wyy
 */

/**
 * 新增和修改自动填充创建时间和修改时间字段,无须手动赋值
 */
@Component
public class EasyrayMetaObjectHandler implements MetaObjectHandler {

    private final Logger logger = LoggerFactory.getLogger(EasyrayMetaObjectHandler.class);

    @DubboReference
    private IdService idService;

    @Override
    public void insertFill(MetaObject metaObject) {
        String createDate = metaObject.findProperty(FieldNameConstant.createDate, true);
        if (createDate != null) {
            Object value = this.getFieldValByName(createDate, metaObject);
            if (value == null) {
                logger.debug("{} auto set {} value: {}", metaObject.getOriginalObject().getClass().getSimpleName(), FieldNameConstant.createDate, new Date());
                this.setFieldValByName(FieldNameConstant.createDate, new Date(), metaObject);
            }
        }

        String modifiedDate = metaObject.findProperty(FieldNameConstant.modifiedDate, true);
        if (modifiedDate != null) {
            Object value = this.getFieldValByName(modifiedDate, metaObject);
            if (value == null) {
                logger.debug("{} auto set {} value: {}", metaObject.getOriginalObject().getClass().getSimpleName(), FieldNameConstant.modifiedDate, new Date());
                this.setFieldValByName(FieldNameConstant.modifiedDate, new Date(), metaObject);
            }
        }
        String id = metaObject.findProperty(FieldNameConstant.id, true);
        if (id != null) {
            Object value = this.getFieldValByName(id, metaObject);
            if (value == null) {
                logger.debug("{} auto set {} value: {}", metaObject.getOriginalObject().getClass().getSimpleName(), FieldNameConstant.modifiedDate, new Date());
                this.setFieldValByName(FieldNameConstant.id, idService.nextId(metaObject.getOriginalObject().getClass().getName()), metaObject);
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String modifiedDate = metaObject.findProperty(FieldNameConstant.modifiedDate, true);
        if (modifiedDate != null) {
            Object value = this.getFieldValByName(modifiedDate, metaObject);
            if (value == null) {
                logger.debug("auto set {} value: {}", FieldNameConstant.modifiedDate, new Date());
                this.setFieldValByName(FieldNameConstant.modifiedDate, new Date(), metaObject);
            }
        }
    }
}
