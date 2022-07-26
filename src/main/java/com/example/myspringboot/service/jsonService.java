package com.example.myspringboot.service;

import com.example.myspringboot.entity.Json;

import java.util.List;

public interface jsonService {

    public int insert(Json json);

    public List<Json> selectAll();

    public Json findOnebyName(String name);


    public int delete(String name);
}
