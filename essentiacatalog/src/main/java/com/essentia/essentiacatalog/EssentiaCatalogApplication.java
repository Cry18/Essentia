package com.essentia.essentiacatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// The catalog is public — UserDetailsService not needed.
// The JwtFilter is present to populate the SecurityContext if the client sends a token.
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class EssentiaCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(EssentiaCatalogApplication.class, args);
	}

}
