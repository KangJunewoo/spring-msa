package socialmultiplication.multiplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import socialmultiplication.multiplication.domain.Multiplication;
import socialmultiplication.multiplication.domain.MultiplicationResultAttempt;
import socialmultiplication.multiplication.domain.User;
import socialmultiplication.multiplication.event.EventDispatcher;
import socialmultiplication.multiplication.event.MultiplicationSolvedEvent;
import socialmultiplication.multiplication.repository.MultiplicationResultAttemptRepository;
import socialmultiplication.multiplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
class MultiplicationServiceImpl implements MultiplicationService {
    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;
    private EventDispatcher eventDispatcher;


    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
                                     final MultiplicationResultAttemptRepository attemptRepository,
                                     final UserRepository userRepository,
                                     final EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }


    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        // ????????? ????????? ??????
        Assert.isTrue(!attempt.isCorrect(), "????????? ????????? ?????? ??? ????????????!");

        Multiplication result = attempt.getMultiplication();
        int factorA = result.getFactorA();
        int factorB = result.getFactorB();

        boolean isCorrect = (attempt.getResultAttempt() == factorA * factorB);

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                attempt.getMultiplication(),
                attempt.getResultAttempt(),
                isCorrect
        );

        attemptRepository.save(checkedAttempt);

        eventDispatcher.send(
                new MultiplicationSolvedEvent(checkedAttempt.getId(),
                        checkedAttempt.getUser().getId(),
                        checkedAttempt.isCorrect()
                )
        );

        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public MultiplicationResultAttempt getResultById(Long resultId) {
        // TODO: ????????? ????????????? ????????? ?????? ??? ?????? ??????????????? ??????.
        return attemptRepository.findById(resultId).orElseThrow();
    }
}
