package com.linocks.flight.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "charges")
public class Charges{
    private String id;
    private double charge;
    private String taxId;    
}