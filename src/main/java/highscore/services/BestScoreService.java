package highscore.services;

import highscore.entities.Highscore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "BestScore", url = "$BEST_SCORE_URL")
public interface BestScoreService {
    @RequestMapping(method = RequestMethod.POST, value = "/submitNewHighest")
    public void submitNewHighest(Highscore score);
}
