package highscore.repositories;

import highscore.entities.Highscore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HighscoreRepository extends JpaRepository<Highscore, Long> {
}
