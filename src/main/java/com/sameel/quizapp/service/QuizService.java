package com.sameel.quizapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sameel.quizapp.model.Question;
import com.sameel.quizapp.model.QuestionWrapper;
import com.sameel.quizapp.model.Quiz;
import com.sameel.quizapp.model.Response;
import com.sameel.quizapp.repo.QuestionRepo;
import com.sameel.quizapp.repo.QuizRepo;

@Service
public class QuizService {

    private QuizRepo quizRepo;
    
    private QuestionRepo questionRepo;

    public QuizService(QuizRepo quizRepo, QuestionRepo questionRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<String> createQuiz(String category, Integer numOfQs, String title) {

        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions.stream().limit(numOfQs).collect(Collectors.toList()));
        quizRepo.save(quiz);

        return new ResponseEntity<>("Quiz was created and saved\nYour Quiz Id: " + quiz.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {

        
        List<Question> quizQuestionsFromDB = getQuestionsByQuizId(quizId);
        
        if(quizQuestionsFromDB.isEmpty())
            return new ResponseEntity<>(Arrays.asList(new QuestionWrapper(-1, null, null, null, null, null)), HttpStatus.NOT_FOUND);        
        
        List<QuestionWrapper> quizQuestionsForUser = new ArrayList<>();

        for(Question q : quizQuestionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getQid(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            quizQuestionsForUser.add(qw);
        }

        return new ResponseEntity<>(quizQuestionsForUser, HttpStatus.OK);
    }

    public Integer calculateResult(Integer quizId, List<Response> userResponses) {
        
        int score = 0;
        int i = 0;
        
        List<Question> quizQuestions = getQuestionsByQuizId(quizId);

        if(quizQuestions.isEmpty())
            return -1; 

        for(Response r : userResponses) {
            if(r.getResponse().equals(quizQuestions.get(i).getRightAnswer()))
                score++;
            i++;
        }

        return score;
    }


    public List<Question> getQuestionsByQuizId(Integer quizId) {
        Optional<Quiz> quiz = quizRepo.findById(quizId);
        try { return quiz.get().getQuestions(); } 
        catch(NoSuchElementException e) { return new ArrayList<>(); }    
    }

    public ResponseEntity<String> deleteQuiz(Integer quizId) {
        quizRepo.deleteById(quizId);
        return new ResponseEntity<>("quiz " + quizId + " deleted", HttpStatus.ACCEPTED);
    }
}
