package com.easyray.documentapi.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easyray.baseapi.provider.BaseLocalProvider;
import com.easyray.documentapi.entity.DFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Date: 2020-02_13
 * @Author: wyy
 */
public interface DFileLocalProvider extends BaseLocalProvider<DFile> {

    IPage<DFile> findByName(IPage<DFile> page, String name, long tenantId);

    IPage<DFile> findByFolderId(IPage<DFile> page, long folderId, long tenantId);

    List<DFile> findByFolderId(long folderId, long tenantId);

    String uploadFile(MultipartFile file) throws IOException;

}
