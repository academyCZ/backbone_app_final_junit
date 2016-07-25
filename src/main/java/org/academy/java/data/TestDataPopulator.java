package org.academy.java.data;

import org.academy.java.entity.Answer;
import org.academy.java.entity.Interview;
import org.academy.java.entity.Question;
import org.academy.java.repository.AnswerRepository;
import org.academy.java.repository.InterviewRepository;
import org.academy.java.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

@Component
public class TestDataPopulator {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @PostConstruct
    public void init() {

        questionRepository.deleteAll();
        answerRepository.deleteAll();
        interviewRepository.deleteAll();

        Interview interview1 = new Interview();
        interview1.setName("Interview for Java Spring programmer");
        interview1.setDescription("This interview is supposed to test knowledge of spring especially spring-boot and spring-rest.");
        interview1.setState(Interview.State.DRAFT);
        interview1.setEvaluationDescription("You have 60 minutes for complete the test. In multi-choice question the wrong answers are evaluated by negative points ");
        interviewRepository.save(interview1);

        Interview interview2 = new Interview();
        interview2.setName("Interview for Adastra CEO");
        interview2.setDescription("This interview is supposed to test knowledge of spring especially spring-boot and spring-rest.");
        interview2.setState(Interview.State.DRAFT);
        interview2.setEvaluationDescription("You have 60 minutes for complete the test. In multi-choice question the wrong answers are evaluated by negative points ");
        interviewRepository.save(interview2);


        Question q1 = new Question();
        q1.setText("What's new in Java 8");
        q1.setQuestionType(Question.QuestionType.CHECKBOX);
        q1.setInterview(interview1);
        q1.setPosition(1);
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setText("What's new in Java 7");
        q2.setQuestionType(Question.QuestionType.TEXT_AREA);
        q2.setInterview(interview2);
        q2.setPosition(2);
        questionRepository.save(q2);

        Question q3 = new Question();
        q3.setText("What's new in Java 6");
        q3.setQuestionType(Question.QuestionType.RADIO);
        q3.setInterview(interview2);
        q3.setPosition(1);
        questionRepository.save(q3);


        Answer a1 = new Answer();
        a1.setText("lambdas");
        a1.setCorrect(true);
        a1.setQuestion(q1);

        Answer a2 = new Answer();
        a2.setText("exception multi-catch");
        a2.setCorrect(true);
        a2.setQuestion(q1);

        Answer a3 = new Answer();
        a3.setText("Write your text answer here...");
        a3.setCorrect(true);
        a3.setQuestion(q2);

        Answer a4 = new Answer();
        a4.setText("Generics");
        a4.setCorrect(true);
        a4.setQuestion(q3);

        Answer a5 = new Answer();
        a5.setText("JAX-WS 2.0");
        a5.setCorrect(true);
        a5.setQuestion(q3);

        answerRepository.save(asList(a1, a2, a3, a4, a5));
    }
}
