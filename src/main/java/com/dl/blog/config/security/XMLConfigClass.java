package com.dl.blog.config.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:spring-security-context.xml"})
public class XMLConfigClass {

}