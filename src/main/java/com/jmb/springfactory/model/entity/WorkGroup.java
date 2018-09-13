package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table
public class WorkGroup extends BaseEntity {

    private String name;
    private String startHour;
    private String finishHour;
}
