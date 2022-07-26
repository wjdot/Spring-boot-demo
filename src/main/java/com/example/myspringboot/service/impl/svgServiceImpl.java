package com.example.myspringboot.service.impl;


import com.example.myspringboot.entity.Json;
import com.example.myspringboot.entity.Svg;
import com.example.myspringboot.repository.svgRepository;
import com.example.myspringboot.service.svgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class svgServiceImpl implements svgService {
    @Autowired
    private svgRepository repository;

    public int insert(Svg svg){
        this.repository.save(svg);
        return 1;
    }

    public List<Svg> selectAll(){

             return this.repository.findAll();
    }


    public Svg findOnebyName(String name){

        return this.repository.findByName(name);
    }

    public int delete(String name){
        Svg svg=this.repository.findByName(name);
        if(svg!=null){
            String path=svg.getSvg_data();
            File file=null;
            try {
                file=new File(path);
                if(file.exists())file.delete();
            }catch (Exception e){
                System.out.println("文件不存在");
                e.printStackTrace();
            }
            this.repository.delete(svg);
            return 1;
        }
        else return 0;
    }
}
