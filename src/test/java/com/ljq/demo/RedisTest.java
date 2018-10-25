package com.ljq.demo;

import com.internship.StartApplication;
import com.internship.mvc.repository.JedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class RedisTest {
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Test
    public void getToken(){
        String a= jedisClient.get(REDIS_USER_SESSION_KEY+":"+"9de45afc-4bc9-4e05-87a6-4241e1379079");
        System.out.printf(a);
    }
}
