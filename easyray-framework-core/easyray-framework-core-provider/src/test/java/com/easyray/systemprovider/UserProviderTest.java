package com.easyray.systemprovider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.easyray.coreapi.entity.User;
import com.easyray.idgeneratorapi.provider.IdService;
import com.easyray.systemprovider.provider.impl.UserLocalProviderImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Date: 2020/6/6
 * @Author: wyy
 */
@SpringBootTest
public class UserProviderTest {
    private Logger logger = LoggerFactory.getLogger(UserProviderTest.class);

    @Reference
    private IdService idService;

    @Autowired
    private UserLocalProviderImpl userLocalProviderImpl;

    @Test
    public void testAddUser() {
        User user = new User(idService.nextId(User.class.getName()));
        user.setUsername(System.currentTimeMillis() + "")
                .setPassword("123456");
        user.setUserId(user.getId())
                .setFullName(user.getUsername());
        userLocalProviderImpl.save(user);
    }
}
