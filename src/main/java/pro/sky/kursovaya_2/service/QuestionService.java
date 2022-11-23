package pro.sky.kursovaya_2.service;

import pro.sky.kursovaya_2.model.Question;

import java.util.Collection;

public interface QuestionService {

    Question add(String question, String answer);
    Question add(Question question);

    Question remove(Question question);

    Question getRandomQuestion();

    Collection<Question> getAll();
}
