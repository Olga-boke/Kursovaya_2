package pro.sky.kursovaya_2.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.kursovaya_2.exception.QuestionAlreadyExistException;
import pro.sky.kursovaya_2.exception.QuestionNotFoundException;
import pro.sky.kursovaya_2.model.Question;
import pro.sky.kursovaya_2.service.QuestionService;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Random random;

    private final Set<Question> questions;

    public JavaQuestionService(){
        this.random = new Random();
        this.questions = new HashSet<>();
    }
    @Override
    public Question add(String question,
                        String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if(!questions.contains(question)) {
            throw new QuestionAlreadyExistException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.size() == 0) {
            return null;
        }
        return questions.stream().skip(random.nextInt(questions.size()))
                .findAny()
                .orElse(null);
    }


}
