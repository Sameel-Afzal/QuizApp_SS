package com.sameel.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sameel.quizapp.model.Question;
import com.sameel.quizapp.repo.QuestionRepo;

@Service
public class QuestionService {

    
    private QuestionRepo questionRepo; 
    
    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {

        try {
            return new ResponseEntity<>(questionRepo.findAll(Sort.by(Direction.ASC, "qid")), HttpStatus.FOUND);
        } catch(Exception e) { e.printStackTrace(); }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }
 
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try {
            return new ResponseEntity<>(questionRepo.findByCategoryOrderByQid(category), HttpStatus.FOUND);
        } catch(Exception e) { e.printStackTrace(); }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Question> updateQuestionIfExists(String operation, Question question) {    

        if(operation.equals("add") || operation.equals("update")) {
            try {  
                questionRepo.save(question);
                return new ResponseEntity<>(question, HttpStatus.CREATED);
            }
            catch(Exception e) {e.printStackTrace();}  
        }
        return new ResponseEntity<>(question, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer qId) {
        
        questionRepo.deleteById(qId);
        return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);
    }
}
