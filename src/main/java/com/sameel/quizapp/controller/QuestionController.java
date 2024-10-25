package com.sameel.quizapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sameel.quizapp.model.Question;
import com.sameel.quizapp.service.QuestionService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin
@RequestMapping("question")
public class QuestionController {

    
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Welcome to my Quiz App project. Frontend is not integrated yet, So I am using search bar and postman to ensure API functionalities. Thanks for visiting.", HttpStatus.OK);
    }
    
    @GetMapping("getAll")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PutMapping("{operation}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String operation, @RequestBody Question question) {
            return questionService.updateQuestionIfExists(operation, question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") String qId) {
        return questionService.deleteQuestion(Integer.parseInt(qId));
    }
}

