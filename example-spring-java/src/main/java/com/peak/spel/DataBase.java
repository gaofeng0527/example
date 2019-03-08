package com.peak.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.properties")
public class DataBase {

    String url;

    public void setUrl(@Value("${mysql.url}") String url) {
        this.url = url;
    }

    public void getCon() {
        System.out.println(url);

    }
}
