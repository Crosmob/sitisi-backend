package com.sitisimenu;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.sitisimenu.models.Menu;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories("com.sitisimenu.repositories")
@Import(RepositoryRestMvcConfiguration.class)
public class MyConfiguration extends RepositoryRestMvcConfiguration {
	
	@Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Menu.class);
    }
}
