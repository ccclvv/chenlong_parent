package com.chenlong;

import com.chenlong.user.UserApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class})
public class UserAppTest {

    @Autowired
    //只能存string类型的数据   对象要转成json格式存储
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void stringRedisTemplateTest(){
        stringRedisTemplate.opsForValue().set("name:", "童昊");
    }

}
