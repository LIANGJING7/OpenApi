package com.jing.project.service;

import com.jing.jingclientsdk.client.JingApiClient;
import com.jing.project.jingcommon.model.entity.InterfaceInfoInvoke;
import com.jing.project.jingcommon.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author jing
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;
    @Autowired
    private JingApiClient jingApiClient;
    @Test
    void testAddUser() {
        User user = new User();
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }


    @Test
    public void test(){
        InterfaceInfoInvoke interfaceInfoInvoke = new InterfaceInfoInvoke();
        System.out.println(interfaceInfoInvoke.toString());


    }
}
