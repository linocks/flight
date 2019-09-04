package com.linocks.flight.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "tax")
@Data
public class Tax{
    private String id;
    public String taxName;

}