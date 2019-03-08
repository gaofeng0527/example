package com.chapter.service.impl;

import com.chapter.entity.Spittle;
import com.chapter.service.SpittleResporty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpittleResportyImpl implements SpittleResporty {

    @Override
    public List<Spittle> findSpittles(long max, int count) {
        List<Spittle> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            list.add(new Spittle((long)i, "spittle" + i, new Date()));
        }
        return list;
    }
}
