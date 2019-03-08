package com.chapter.controller;

import com.chapter.config.WebConfig;
import com.chapter.entity.Spittle;
import com.chapter.service.SpittleResporty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class SpittleControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc moc;

    @Autowired
    SpittleResporty sr;

    @Before
    public void setup() {
        this.moc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * MockMvcResultMatchers 添加验证规则
     * MockMvcResultHandlers 模拟请求头
     * MockMvcRequestBuilders 可以模拟请求
     * @throws Exception
     */
    @Test
    public void spittlesTest() throws Exception {
        moc.perform(MockMvcRequestBuilders.get("/spittles"))
                .andExpect(MockMvcResultMatchers.view().name("spittle/spittles"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("slist"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试获取spittle的方法
     */
    @Test
    public void findSpittles(){
        List<Spittle> list = sr.findSpittles(Long.MAX_VALUE,10);
        assert list != null;
        assert list.size() == 10;
    }

    @Test
    public void registerTest() throws Exception {
        moc.perform(MockMvcRequestBuilders.get("/spitter/register"))
                .andExpect(MockMvcResultMatchers.view().name("spittle/spitterRegisterForm"));

    }
}
