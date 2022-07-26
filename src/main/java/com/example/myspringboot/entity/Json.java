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
@Table(name = "json")
public class Json {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String json_data;

    public Json(String name, String json_data){
        this.name=name;
        this.json_data=json_data;
    }
}
