package org.example.config;

import org.example.Car;
import org.example.DieselEngine;
import org.example.Engine;
import org.example.PetrolEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;


@Configuration
@ComponentScan("org.example")
@PropertySource("classpath:application.properties")
public class BeanConfig {

}
