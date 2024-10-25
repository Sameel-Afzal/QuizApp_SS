package com.sameel.quizapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sameel.quizapp.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findByCategoryOrderByQid(String category);

    

    @Query(value = "SELECT * FROM public.question q WHERE q.category = :category ORDER BY RANDOM()", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category);   

}
