package dao;

import org.springframework.data.jpa.repository.JpaRepository;


import model.Quiz;



public interface QuizDao extends JpaRepository<Quiz,Integer> {

}
