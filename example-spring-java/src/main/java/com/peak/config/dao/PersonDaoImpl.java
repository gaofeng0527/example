package com.peak.config.dao;

public class PersonDaoImpl implements PersonDao {

    @Override
    public void save(Person person){
        System.out.println("save person success");
    }

}
