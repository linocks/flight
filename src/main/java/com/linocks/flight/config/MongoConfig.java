package com.linocks.flight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    private String db;

    @Value("${spring.data.mongodb.host}")
    private String host;
    
//   public @Bean MongoClient mongoClient() {
//       return new MongoClient(host);
//   }

//   public @Bean MongoTemplate mongoTemplate() {
//       return new MongoTemplate(mongoClient(), db);
//     }
    
    // public @Bean ReactiveMongoTemplate mongoTemplate(){
    //     return new ReactiveMongoTemplate(MongoClients.create(), db);
        
    // }


  
}