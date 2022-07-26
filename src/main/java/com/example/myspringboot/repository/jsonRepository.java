package com.example.myspringboot.repository;
import com.example.myspringboot.entity.Json;
import org.springframework.data.jpa.repository.JpaRepository;

public interface jsonRepository extends JpaRepository<Json,Long> {
    public Json findByName(String name);

}

