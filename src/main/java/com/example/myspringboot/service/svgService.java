package com.example.myspringboot.service;

import com.example.myspringboot.entity.Svg;

import java.util.List;

public interface svgService {

    public int insert(Svg svg);

    public List<Svg> selectAll();

    public Svg findOnebyName(String name);

    public int delete(String name);
}
