package com.ljq.demo;

import com.internship.StartApplication;
import com.internship.mvc.pojo.User;
import com.internship.service.UserService;
import com.internship.util.SingleResultUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author itdragon
 * @RunWith 它是一个运行器
 * @RunWith(SpringRunner.class) 表示让测试运行于Spring测试环境，不用启动spring容器即可使用Spring环境
 * @SpringBootTest(classes=StartApplication.class) 表示将StartApplication.class纳入到测试环境中，若不加这个则提示bean找不到。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class SpringbootStudyApplicationTests {
    @Autowired
    private UserService userService;


    @Test    // 测试注册，新增数据
    public void registerUser() {
        User user = new User();
        user.setAccount("ljq");
        user.setUserName("ljqGit");
        user.setEmail("ljqTwinkle@163.com");
        user.setIphone("17621948328");
        user.setPlainPassword("123456789");
        user.setPlatform("WeChat");
        user.setCreatedDate(SingleResultUtil.getCurrentDateTime());
        user.setUpdatedDate(SingleResultUtil.getCurrentDateTime());
        SingleResultUtil.entryptPassword(user);
        System.out.println(user);
        userService.registerUser(user);
    }

}
