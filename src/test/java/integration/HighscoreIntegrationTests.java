package integration;

import highscore.bl.HighscoreBl;
import highscore.controllers.HighscoreController;
import highscore.entities.Highscore;
import highscore.repositories.HighscoreRepository;
import highscore.services.BestScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class HighscoreIntegrationTests {
    private final static String A_SCORE_REQUEST = "{\"username\":\"OfekRv\",\"score\":100}";
    private final static int MAX_SCORES = 5;

    @Mock
    private HighscoreRepository highscoreRepositoryMock;
    @Mock
    private BestScoreService bestScoreServiceMock;
    @InjectMocks
    private HighscoreBl blMock;

    private MockMvc mvc;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(blMock, "maxScores", MAX_SCORES);

        mvc = MockMvcBuilders
                .standaloneSetup(new HighscoreController(blMock))
                .build();
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscore_then_trueIsReturned() throws Exception {
        performSubmit(A_SCORE_REQUEST).andExpect(content().string("true"));
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscore_then_responseStatusIs200() throws Exception {
        performSubmit(A_SCORE_REQUEST).andExpect(status().isOk());
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNotNewHighscore_then_falseIsReturned() throws Exception {
        mockFullHighscoreList();
        performSubmit(A_SCORE_REQUEST).andExpect(content().string("false"));
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNotNewHighscore_then_responseStatusIs200() throws Exception {
        mockFullHighscoreList();
        performSubmit(A_SCORE_REQUEST).andExpect(status().isOk());
    }

    private void mockFullHighscoreList() {
        given(highscoreRepositoryMock.findAll()).willReturn(asList(new Highscore("OfekRv", 100),
                new Highscore("OfekRv", 200),
                new Highscore("OfekRv", 300),
                new Highscore("OfekRv", 400),
                new Highscore("OfekRv", 500)));
        given(highscoreRepositoryMock.count()).willReturn((long) MAX_SCORES);
    }

    private ResultActions performSubmit(String score) throws Exception {
        return mvc.perform(post("/api/highscores/actions/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(score));
    }
}
