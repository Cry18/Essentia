package com.essentia.essentiaadministration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// The admin module does not expose authentication endpoints — UserDetailsService not needed.
// Authentication is handled via JWT issued by essentiauser.
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class EssentiaAdministrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EssentiaAdministrationApplication.class, args);
	}

}
