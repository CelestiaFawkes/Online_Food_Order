package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dao.QuestionsDao;
import model.Questions;

@Service
public class QuestionServices {
	
	@Autowired
	QuestionsDao questiondao;
	
	public ResponseEntity<List<Questions>> getAllQuestion()
	{
		try {
			return new ResponseEntity<>(questiondao.findAll(), HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<List<Questions>> getQuestionsByCategory(String category){
		
		try {
			return new ResponseEntity<>(questiondao.findByCategory(category),HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<String> addQuestion(Questions question)
	{
		
		questiondao.save(question);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}
	
	
	
	

}
