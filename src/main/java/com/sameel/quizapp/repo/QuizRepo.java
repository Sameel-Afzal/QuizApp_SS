package com.sameel.quizapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sameel.quizapp.model.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer>{
    
}
