package com.project.chamjimayo;

import com.project.chamjimayo.security.config.ApiProperties;
import com.project.chamjimayo.security.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		JwtProperties.class, ApiProperties.class
})
public class ChamjimayoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChamjimayoApplication.class, args);
	}

}
