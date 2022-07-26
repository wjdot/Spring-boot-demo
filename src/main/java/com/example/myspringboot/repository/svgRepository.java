package com.example.myspringboot.repository;
import com.example.myspringboot.entity.Svg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface svgRepository extends JpaRepository<Svg,Long> {
    public Svg findByName(String name);
}

