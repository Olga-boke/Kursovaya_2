package pro.sky.kursovaya_2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.kursovaya_2.exception.NotEnoughQuestionsException;
import pro.sky.kursovaya_2.exception.QuestionAlreadyExistException;
import pro.sky.kursovaya_2.exception.QuestionNotFoundException;
import pro.sky.kursovaya_2.model.Question;
import pro.sky.kursovaya_2.service.impl.ExaminerServiceImpl;
import pro.sky.kursovaya_2.service.impl.JavaQuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {

    private final JavaQuestionService javaQuestionService = new JavaQuestionService();

    @Test
    public void add1Test(){
        javaQuestionService.add(new Question("test","test"));

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(()-> javaQuestionService.add(new Question("test","test")));
        assertThat(javaQuestionService.getAll()).containsExactlyInAnyOrder(new Question("test","test"));
    }

    @Test
    public void add2Test(){
        String question = "test";
        String answer = "test";
        Question q = new Question(question, answer);
        javaQuestionService.add(question, answer);

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(()-> javaQuestionService.add(question,answer));
        assertThat(javaQuestionService.getAll()).containsExactlyInAnyOrder(q);
    }

    @Test
    public void removeTest(){
        javaQuestionService.add(new Question("test", "test"));
        javaQuestionService.remove(new Question("test","test"));

        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(()->javaQuestionService.remove(new Question("test","test")));
    }

    @ParameterizedTest
    @MethodSource("questions")
    public void getRandomQuestionTest(Set<Question> questios) {
        questios.forEach(javaQuestionService::add);

        assertThat(javaQuestionService.getRandomQuestion()).isIn(javaQuestionService.getAll());
    }
    public static Stream<Arguments> questions() {
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new Question("Questioh1", "Answer1"),
                                new Question("Questioh2", "Answer2"),
                                new Question("Questioh3", "Answer13")
                        )
                )
        );

    }
}
