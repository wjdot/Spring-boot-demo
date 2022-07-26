package com.example.myspringboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "svg")
public class Svg {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String svg_data;

    public Svg(String name, String svg_data){
        this.name=name;
        this.svg_data=svg_data;
    }
}
