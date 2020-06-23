package com.easyray.documentprovider.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyray.baseapi.provider.EasyrayServiceImpl;
import com.easyray.documentapi.entity.DFile;
import com.easyray.documentapi.entity.DFolder;
import com.easyray.documentapi.provider.DFileLocalProvider;
import com.easyray.documentapi.provider.DFolderLocalProvider;
import com.easyray.documentprovider.mapper.DFolderMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyy
 * @since 2020-02_13
 */
@DubboService
@Service
public class DFolderLocalProviderImpl extends EasyrayServiceImpl<DFolderMapper, DFolder> implements DFolderLocalProvider {


    @Autowired
    @Qualifier("DFileLocalProviderImpl")
    private DFileLocalProvider dFileLocalProvider;

    @Override
    public void deleteFolder(long id) {
        dFileLocalProvider.remove(new QueryWrapper<DFile>().lambda().eq(DFile::getFolderId, id));
        List<DFolder> subFolderList = getSubFolderList(id);
        subFolderList.forEach(folder -> deleteFolder(folder.getId()));
        removeById(id);
    }

    @Override
    public List<DFolder> getSubFolderList(long parentFolderId) {

        return getBaseMapper().fetchByQuery(new QueryWrapper<DFolder>().lambda().eq(DFolder::getParentId, parentFolderId));
    }
}
