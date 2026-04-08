package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "example_repair")
@Data
public class Repair {


    @Id
    private String principal;

    private String state;

    private Long timestamp;
}