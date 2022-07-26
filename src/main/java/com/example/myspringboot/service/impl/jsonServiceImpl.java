package com.example.myspringboot.service.impl;


import com.example.myspringboot.entity.Json;
import com.example.myspringboot.repository.jsonRepository;
import com.example.myspringboot.service.jsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class jsonServiceImpl implements jsonService {
    @Autowired
    private jsonRepository repository;

    public int insert(Json json){

        this.repository.save(json);
        return 1;
    }

    public List<Json> selectAll(){

             return this.repository.findAll();
    }


    public Json findOnebyName(String name){

        return this.repository.findByName(name);
    }

    @Override
    public int delete(String name) {
        Json json=this.repository.findByName(name);
        if(json!=null){
            this.repository.delete(json);
            return 1;
        }
        else return 0;
    }
}
