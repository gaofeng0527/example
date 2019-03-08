package com.peak.config;

import com.peak.config.dao.Person;
import com.peak.config.dao.PersonDao;

public class PersonService {

    PersonDao dao;

    public void setDao(PersonDao dao){
        this.dao = dao;
    }

    public PersonService(PersonDao dao){
        this.dao = dao;
    }

    public void say() {
        System.out.println("你好，欢迎来到地球");
        dao.save(new Person());
    }
}
