package com.chapter.service;

import com.chapter.entity.Spittle;

import java.util.List;

public interface SpittleResporty {

    List<Spittle> findSpittles(long max, int count);
}
