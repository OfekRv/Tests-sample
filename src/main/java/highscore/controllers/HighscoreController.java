package highscore.controllers;

import highscore.bl.HighscoreBl;
import highscore.entities.Highscore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("api/highscores/actions")
public class HighscoreController {
    @Inject
    private HighscoreBl bl;

    @PostMapping("submit")
    public boolean submit(@RequestBody Highscore score) {
        return bl.submit(score);
    }
}
