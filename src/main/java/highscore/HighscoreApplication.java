package highscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HighscoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(HighscoreApplication.class, args);
    }
}
