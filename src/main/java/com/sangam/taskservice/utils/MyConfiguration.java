	package com.sangam.taskservice.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class MyConfiguration {
	 private String name;
	 public static KieServices kieServices=KieServices.Factory.get();
	 
	 	@Bean
	    public KieContainer getKieContainer(){

	 		System.out.println("Loading bean kie ");
	    	KieServices kieServices=KieServices.Factory.get();
	        getKieRepository();
	        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

	        Resource dt = ResourceFactory.newClassPathResource("com/sangam/taskservice/TaskRules1.xls",getClass());
	        kieFileSystem.write(dt);
	        
	        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
	        kb.buildAll();
	        KieRepository k = kieServices.getRepository(); 
	        return kieServices.newKieContainer(k.getDefaultReleaseId());

	    }

	    private static void getKieRepository() {
	        final KieRepository kieRepository = kieServices.getRepository();
	        kieRepository.addKieModule(new KieModule() {
	        	
	        	
	                        public ReleaseId getReleaseId() {
	                return kieRepository.getDefaultReleaseId();
	            }
	        });
	    }

}
