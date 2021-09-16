package gamification.event;

import gamification.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class EventHandler {
    private GameService gameService;

    EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${multiplication.queue}")
    void handleMultiplicationSolved(final MultiplicationSolvedEvent event) {
        log.info("multiplicationsolvedevent 수신 : {}", event.getMultiplicationResultAttemptId());

        try {
            gameService.newAttemptForUser(event.getUserId(),
                    event.getMultiplicationResultAttemptId(),
                    event.isCorrect());

        } catch (final Exception e) {
            log.error("multiplicationsolvedevent 처리 중 에러", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
