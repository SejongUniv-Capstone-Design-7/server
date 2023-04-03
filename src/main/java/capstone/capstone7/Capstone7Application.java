package capstone.capstone7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Capstone7Application {

	public static void main(String[] args) {
		SpringApplication.run(Capstone7Application.class, args);
	}

}
