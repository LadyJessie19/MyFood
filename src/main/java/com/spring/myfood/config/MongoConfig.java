package com.spring.myfood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.spring.myfood.repository")
public class MongoConfig {

}
