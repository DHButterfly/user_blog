package com.dl.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void contextLoads()  {
        String[] names = applicationContext.getBeanDefinitionNames();
        for(String name:names){
            System.out.println("容器中的bean："+name);

        }
    }

}
