package highscore.bl;

import highscore.entities.Highscore;
import highscore.repositories.HighscoreRepository;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.Comparator;

@Named
public class HighscoreBl {
    @Inject
    private HighscoreRepository repository;

    @Value("${MAX_SCORES}")
    private int maxScores;

    public boolean submit(Highscore score) {
        score.setTimestamp(LocalDateTime.now());

        if (repository.count() < maxScores) {
            repository.save(score);
            return true;
        }

        Highscore minScore = repository.findAll().stream().min(Comparator.comparingLong(Highscore::getScore)).get();
        if (score.getScore() > minScore.getScore()) {
            repository.delete(minScore);
            repository.save(score);
            return true;
        }

        return false;
    }
}
