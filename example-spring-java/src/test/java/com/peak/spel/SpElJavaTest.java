package com.peak.spel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpELJavaConfig.class)
public class SpElJavaTest {

    @Autowired
    Person person;

    @Autowired
    DataBase db;

    @Test
    public void spelTest(){
        assert person != null;
        person.say();

        assert db != null;
        db.getCon();
    }

    @Test
    public void randomTest(){
        System.out.println((int) (Math.random() * 10000000));
    }
}
