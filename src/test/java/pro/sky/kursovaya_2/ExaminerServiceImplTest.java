package pro.sky.kursovaya_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.kursovaya_2.exception.NotEnoughQuestionsException;
import pro.sky.kursovaya_2.model.Question;
import pro.sky.kursovaya_2.service.impl.ExaminerServiceImpl;
import pro.sky.kursovaya_2.service.impl.JavaQuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final List<Question> javaQuestions = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        javaQuestions.clear();

        javaQuestions.addAll(
                Stream.of(
                        new Question("Вопрос - 1", "ответ - 1"),
                        new Question("Вопрос - 2", "ответ - 2"),
                        new Question("Вопрос - 3", "ответ - 3")
                ).collect(Collectors.toList())
        );
        when(javaQuestionService.getAll()).thenReturn(javaQuestions);
    }

    @Test
    public void getQuestionsNegativeTest() {
        assertThatExceptionOfType(NotEnoughQuestionsException.class)
                .isThrownBy(()-> examinerService.getQuestions(-1));
    }

    @Test
    public void getQuestionsPositiveTest(){
        when(javaQuestionService.getRandomQuestion()).thenReturn(
                new Question("Вопрос - 1", "ответ - 1"),
                new Question("Вопрос - 2", "ответ - 2"),
                new Question("Вопрос - 1", "ответ - 1"),
                new Question("Вопрос - 1", "ответ - 1"),
                new Question("Вопрос - 3", "ответ - 3")
        );

        assertThat(examinerService.getQuestions(3))
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Question("Вопрос - 1", "ответ - 1"),
                        new Question("Вопрос - 2", "ответ - 2"),
                        new Question("Вопрос - 3", "ответ - 3")
                );
    }
}
