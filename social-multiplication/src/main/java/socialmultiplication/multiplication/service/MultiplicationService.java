package socialmultiplication.multiplication.service;

import socialmultiplication.multiplication.domain.Multiplication;
import socialmultiplication.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {
    Multiplication createRandomMultiplication();

    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
}
