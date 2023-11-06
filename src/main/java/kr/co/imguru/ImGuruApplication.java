package kr.co.imguru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImGuruApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImGuruApplication.class, args);
    }

}
