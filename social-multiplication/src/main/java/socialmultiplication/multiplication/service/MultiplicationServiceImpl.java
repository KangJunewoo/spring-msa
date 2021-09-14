package socialmultiplication.multiplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialmultiplication.multiplication.domain.Multiplication;
import socialmultiplication.multiplication.domain.MultiplicationResultAttempt;

@Service
final class MultiplicationServiceImpl implements MultiplicationService {
    private RandomGeneratorService randomGeneratorService;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt) {
        Multiplication result = resultAttempt.getMultiplication();
        int factorA = result.getFactorA();
        int factorB = result.getFactorB();

        return resultAttempt.getResultAttempt() == factorA * factorB;
    }
}
