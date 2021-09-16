package gamification.event;

import gamification.domain.GameStats;
import gamification.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class EventHandlerTest {
    private EventHandler eventHandler;

    @Mock
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventHandler = new EventHandler(gameService);
    }

    @Test
    public void multiplicationAttemptReceivedTest() {
        // given
        Long userId = 1L;
        Long attemptId = 8L;
        boolean correct = true;
        GameStats gameStatsExpected = new GameStats();
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attemptId, userId, correct);
        given(gameService.newAttemptForUser(userId, attemptId, correct)).willReturn(gameStatsExpected);

        // when
        eventHandler.handleMultiplicationSolved(event);

        // then
        verify(gameService).newAttemptForUser(userId, attemptId, correct);
    }
}
