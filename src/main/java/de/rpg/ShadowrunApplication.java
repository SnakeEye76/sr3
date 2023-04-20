package de.rpg;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import liquibase.integration.spring.SpringLiquibase;

@SpringBootApplication
public class ShadowrunApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShadowrunApplication.class, args);
	}

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
	    SpringLiquibase liquibase = new SpringLiquibase();
	    liquibase.setChangeLog("classpath:liquibase/master.xml");
	    liquibase.setDataSource(dataSource);
	    return liquibase;
	}
}
