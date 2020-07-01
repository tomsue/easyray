package com.easyray.documentprovider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyray.common.exception.EasyrayAbstractException;
import com.easyray.common.exception.EntityDeleteFailedException;
import com.easyray.coreapi.entity.Tenant;
import com.easyray.coreapi.entity.User;
import com.easyray.coreapi.service.TenantLocalProvider;
import com.easyray.coreapi.service.UserLocalProvider;
import com.easyray.documentapi.entity.DFile;
import com.easyray.documentapi.entity.DFolder;
import com.easyray.documentapi.provider.DFileLocalProvider;
import com.easyray.documentapi.provider.DFolderLocalProvider;
import com.easyray.fastdfsprovider.FastDFSClient;
import com.easyray.idgeneratorapi.provider.IdService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DocumentProviderApplicationTests {

    @DubboReference
    private IdService idService;
    @DubboReference
    private UserLocalProvider userLocalProvider;

    @Autowired
    @Qualifier("DFolderLocalProviderImpl")
    private DFolderLocalProvider dFolderLocalProvider;
    @Autowired
    @Qualifier("DFileLocalProviderImpl")
    private DFileLocalProvider dFileLocalProvider;
    @DubboReference
    private TenantLocalProvider tenantLocalProvider;
    @Autowired
    private FastDFSClient fastDFSClient;

    private User user;
    private Tenant tenant;

    @BeforeAll
    void before() throws EasyrayAbstractException {
        user = doAddUser();
        tenant = doAddTenant(user);
    }


    @AfterAll
    void after() throws EntityDeleteFailedException {
        tenantLocalProvider.delete(tenant.getId());
        userLocalProvider.delete(user.getId());
    }

    @Test
    void addFolderAndFile() throws EasyrayAbstractException {
        DFolder dFolder = doAddFolder();
        for (int i = 0; i < 5; i++) {
            doAddFile(dFolder);
        }

        IPage<DFile> dFileIPage = dFileLocalProvider.findByFolderId(new Page<>(1, 1), dFolder.getId(), tenant.getId());
        assert dFileIPage.getRecords().size() == 1;

        dFileIPage = dFileLocalProvider.findByFolderId(new Page<>(1, 3), dFolder.getId(), tenant.getId());
        assert dFileIPage.getRecords().size() == 3;

        List<DFile> dFileList = dFileLocalProvider.findByFolderId(dFolder.getId(), tenant.getId());
        assert dFileList.size() > 0;

        dFolderLocalProvider.delete(dFolder.getId());
    }

    @Test
    void uploadFile() throws IOException {
        doUploadFile();
    }

    @Test
    void thumbNail() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("2.jpg");
        String contentType = Files.probeContentType(Paths.get(classPathResource.getURI()));
        MockMultipartFile multipartFile = new MockMultipartFile(classPathResource.getFilename(), classPathResource.getFilename(), contentType, classPathResource.getInputStream());
        String url = fastDFSClient.uploadImageAndCrtThumbImage(multipartFile);
        System.out.println("url = " + url);
    }

    @Test
    void testVersion() throws IOException, EasyrayAbstractException {
        DFolder dFolder = doAddFolder();
        DFile dFile = doAddFile(dFolder);
        System.out.println("dFile = " + dFile);
        ClassPathResource classPathResource = new ClassPathResource("2.jpg");
        String contentType = Files.probeContentType(Paths.get(classPathResource.getURI()));
        MockMultipartFile multipartFile = new MockMultipartFile(classPathResource.getFilename(), classPathResource.getFilename(), contentType, classPathResource.getInputStream());
        String s = dFileLocalProvider.updateFile(dFile, multipartFile);
    }

    private void doUploadFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("2.jpg");
        String contentType = Files.probeContentType(Paths.get(classPathResource.getURI()));
        MockMultipartFile multipartFile = new MockMultipartFile(classPathResource.getFilename(), classPathResource.getFilename(), contentType, classPathResource.getInputStream());
        String url = fastDFSClient.uploadFile(multipartFile);
        System.out.println("url = " + url);
    }

    private DFolder doAddFolder() throws EasyrayAbstractException {
        DFolder dFolder = new DFolder(idService.nextId(DFolder.class.getName()))
                .setName(System.currentTimeMillis() + "");

        dFolder.setTenantId(tenant.getId())
                .setUserId(user.getId())
                .setFullName(user.getFullName())
                .setCreateDate(new Date());
        dFolder.setParentId(0L)
                .setTreePath(dFolder.getId() + "");
        dFolderLocalProvider.add(dFolder);
        System.out.println("dFolder = " + dFolder);
        return dFolder;
    }

    private User doAddUser() throws EasyrayAbstractException {
        User user = new User(idService.nextId(User.class.getName()))
                .setUsername(System.currentTimeMillis() + "")
                .setPasswordAndEncode("test");
        user.setUserId(user.getId())
                .setFullName("test")
                .setCreateDate(new Date());
        userLocalProvider.add(user);
        return user;
    }

    private Tenant doAddTenant(User user) throws EasyrayAbstractException {
        Tenant tenant = new Tenant(idService.nextId(Tenant.class.getName()))
                .setName(System.currentTimeMillis() + "");
        tenant.setUserId(user.getId())
                .setFullName(user.getFullName())
                .setCreateDate(new Date());
        tenantLocalProvider.add(tenant);
        return tenant;
    }


    private DFile doAddFile(DFolder folder) throws EasyrayAbstractException {
        DFile dFile = new DFile(idService.nextId(DFile.class.getName()))
                .setName("123.txt")
                .setExtension("txt")
                .setMediaType("text")
                .setFolderId(folder.getId())
                .setUrl("123")
                .setFolderPath(folder.getTreePath());
        dFile.setTenantId(tenant.getId());

        dFile.setUserId(user.getId())
                .setFullName(user.getFullName())
                .setCreateDate(new Date());
        dFileLocalProvider.add(dFile);
        return dFile;
    }

}
