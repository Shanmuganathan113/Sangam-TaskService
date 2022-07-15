package com.sangam.taskservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.sangam.taskservice.utils.MyConfiguration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwagger2
public class SangamTaskserviceApplication implements CommandLineRunner {
	//t
	@Autowired
    private MyConfiguration myConfig;

	public static void main(String[] args) {
		SpringApplication.run(SangamTaskserviceApplication.class, args);
	}

    public void run(String... args) throws Exception {
        System.out.println("Loaded Environment: " + myConfig.getName());
    }
    
    @Bean
    public Docket productApi() {
       return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors.basePackage("com.sangam.taskservice")).build();
    }
}
