package socialmultiplication.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import socialmultiplication.multiplication.domain.Multiplication;
import socialmultiplication.multiplication.service.MultiplicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;

/**
 * WebMvcTest의 경우 SpringBootTest보다 가볍게, 전체가 아닌 mockmvc 빈을 포함해 컨트롤러와 관련된 설정만 불러옴.
 * 즉 WebMvcTest는 컨트롤러 unit test에 적합하고, 실제 통합테스트에서 SpringBootTest를 쓰는 게 좋음.
 * 실제 네트워크 또한 타지 않음.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationController.class)
class MultiplicationControllerTest {
    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    // initFields로 자동으로 초기화됨.
    private JacksonTester<Multiplication> json;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getRandomMultiplicationTest() throws Exception {
        // given
        given(multiplicationService.createRandomMultiplication())
                .willReturn(new Multiplication(70, 20));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/multiplications/random")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();



        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(json.write(new Multiplication(70, 20)).getJson());

    }
}