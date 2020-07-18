package highscore.bl;

import highscore.entities.Highscore;
import highscore.repositories.HighscoreRepository;
import highscore.services.BestScoreService;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Named
public class HighscoreBl {
    @Inject
    private HighscoreRepository repository;
    @Inject
    private BestScoreService bestScoreService;

    @Value("${MAX_SCORES}")
    private int maxScores;

    @Transactional
    public boolean submit(Highscore score) {
        score.setTimestamp(LocalDateTime.now());

        if (isSpaceAvailableForNewScore()) {
            repository.save(score);
        } else {
            Highscore minScore = findMinScore().get();
            if (score.getScore() > minScore.getScore()) {
                repository.delete(minScore);
                repository.save(score);
            } else {
                return false;
            }
        }

        if (isMaxScore(score)) {
            bestScoreService.submitNewHighest(score);
        }

        return true;
    }

    private boolean isSpaceAvailableForNewScore() {
        return repository.count() < maxScores;
    }

    private Optional<Highscore> findMinScore() {
        return repository.findAll().stream().min(Comparator.comparingLong(Highscore::getScore));
    }

    private Optional<Highscore> findMaxScore() {
        return repository.findAll().stream().max(Comparator.comparingLong(Highscore::getScore));
    }

    private boolean isMaxScore(Highscore score) {
        return score.getScore() == findMaxScore().get().getScore();
    }
}
