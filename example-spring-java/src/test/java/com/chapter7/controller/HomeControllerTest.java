package com.chapter7.controller;

import com.chapter7.config.WebConfig;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class HomeControllerTest {

    /**
     *
     */
    @Autowired
    private WebApplicationContext wac;

    private MockMvc moc;

    @Before
    public void setup() {
        this.moc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void indexTest() throws Exception {
        moc.perform(MockMvcRequestBuilders.get("/chapter7/home/index"))
                .andExpect(MockMvcResultMatchers.view().name("chapter7/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("chapter"))
                .andDo(MockMvcResultHandlers.print());

    }

}
