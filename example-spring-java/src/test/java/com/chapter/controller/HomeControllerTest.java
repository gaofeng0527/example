package com.chapter.controller;

import com.chapter.config.WebConfig;
import com.chapter.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc moc;

    @Before
    public void setup() {
        this.moc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void testHomePage() throws Exception {
        moc.perform(MockMvcRequestBuilders.get("/hello")).//模拟请求
                andExpect(MockMvcResultMatchers.view().name("home"))//断言返回的视图名称
                .andExpect(MockMvcResultMatchers.model().attribute("uname","高峰"))//断言属性值
                .andExpect(MockMvcResultMatchers.model().attributeExists("uname"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void userTeset(){
        User user = new User();
        user.setFirstName("123");
    }

}
