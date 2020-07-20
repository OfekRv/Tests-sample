package unit;

import highscore.bl.HighscoreBl;
import highscore.entities.Highscore;
import highscore.repositories.HighscoreRepository;
import highscore.services.BestScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HighscoreBlTests {
    private final static Highscore A_SCORE = new Highscore("OfekRv", 100);
    private final static Highscore A_BEST_SCORE = new Highscore("OfekRv", 1000);
    private final static Highscore A_LOWEST_SCORE = new Highscore("OfekRv", 500);
    private final static int MAX_SCORES = 5;
    private final static int ONE_CALL = 1;

    @Mock
    private HighscoreRepository highscoreRepositoryMock;
    @Mock
    private BestScoreService bestScoreServiceMock;
    @InjectMocks
    private HighscoreBl bl;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(bl, "maxScores", MAX_SCORES);
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscore_then_trueIsReturned() throws Exception {
        boolean actual = bl.submit(A_SCORE);
        assertTrue(actual);
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscore_then_scoreIsSaved() throws Exception {
        bl.submit(A_SCORE);
        verify(highscoreRepositoryMock, times(ONE_CALL)).save(any());
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNotNewHighscore_then_falseIsReturned() throws Exception {
        mockFullHighscoreList();
        boolean actual = bl.submit(A_SCORE);
        assertFalse(actual);
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNotNewHighscore_then_scoreIsNotSaved() throws Exception {
        mockFullHighscoreList();
        bl.submit(A_SCORE);
        verify(highscoreRepositoryMock, never()).save(any());
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscoreAndFullScoreBoard_then_trueIsReturned() throws Exception {
        mockFullHighscoreList();
        boolean actual = bl.submit(A_BEST_SCORE);
        assertTrue(actual);
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscoreAndFullScoreBoard_then_scoreIsSaved() throws Exception {
        mockFullHighscoreList();
        bl.submit(A_BEST_SCORE);
        verify(highscoreRepositoryMock, times(ONE_CALL)).save(any());
    }

    @Test
    public void when_submitCalled_with_score_given_scoreIsNewHighscoreAndFullScoreBoard_then_lowestScoreIsDeleted() throws Exception {
        mockFullHighscoreList();
        bl.submit(A_BEST_SCORE);
        verify(highscoreRepositoryMock, times(ONE_CALL)).delete(any());
    }

    private void mockFullHighscoreList() {
        given(highscoreRepositoryMock.findAll()).willReturn(asList(new Highscore("OfekRv", 100),
                new Highscore("OfekRv", 200),
                new Highscore("OfekRv", 300),
                new Highscore("OfekRv", 400),
                new Highscore("OfekRv", 500)));
        given(highscoreRepositoryMock.count()).willReturn((long) MAX_SCORES);
    }
}
