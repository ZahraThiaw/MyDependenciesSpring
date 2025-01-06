package sn.zahra.thiaw.mydependenciesspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MydependenciesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydependenciesSpringApplication.class, args);
	}

}
