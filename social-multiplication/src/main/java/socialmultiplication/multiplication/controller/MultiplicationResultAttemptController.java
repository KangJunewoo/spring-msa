package socialmultiplication.multiplication.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialmultiplication.multiplication.domain.MultiplicationResultAttempt;
import socialmultiplication.multiplication.service.MultiplicationService;

@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {
    private final MultiplicationService multiplicationService;

    @Autowired
    MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    ResponseEntity<ResultResponse> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
        return ResponseEntity.ok(
                new ResultResponse(multiplicationService.checkAttempt(multiplicationResultAttempt))
        );
    }

    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    @Getter
    static final class ResultResponse {
        private final boolean correct;
    }

}
