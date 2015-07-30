package com.sitisimenu;

import org.springframework.boot.SpringApplication;

//https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples
//http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/
//http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#common-application-properties
//http://www.programming-free.com/2014/07/spring-data-rest-with-angularjs-crud.html
//http://angular-ui.github.io/bootstrap/
//http://docs.spring.io/spring-data/jpa/docs/1.6.4.RELEASE/reference/html/repositories.html
//http://docs.spring.io/spring-data/jpa/docs/1.6.4.RELEASE/reference/html/jpa.repositories.html
//http://www.infoq.com/articles/microframeworks1-spring-boot
//http://thoughtfulsoftware.wordpress.com/2014/01/05/setting-up-https-for-spring-boot/
//http://docs.oracle.com/cd/E19509-01/820-3503/ggfen/index.html
//https://github.com/arnaldop/enhanced-pet-clinic/blob/master/src/main/resources/application.properties
//https://github.com/spring-projects/spring-data-rest/wiki/Configuring-the-REST-URL-path

//keytool -keystore clientkeystore -genkey -alias client

//curl --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus"
//curl --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "https://localhost:8888/menus" --insecure

public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(MyConfiguration.class);
		app.setShowBanner(false);
		app.run(args);
	}
}