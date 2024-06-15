package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dao.QuestionsDao;
import dao.QuizDao;
import model.Questions;
import model.Quiz;
import model.QuizWrapper;
import model.Response;

@Service
public class QuizServices {
	
	@Autowired
	QuizDao quizdao;
	@Autowired
	QuestionsDao questionsdao;
	
	public ResponseEntity<String> createquiz(String category, int numq, String title)
	{ 
		List<Questions> questions = questionsdao.findRandomQuestionsByCategory(category, numq);
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestion(questions);
		quizdao.save(quiz);
		
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}
	
	public ResponseEntity<List<QuizWrapper>> getQuizQuestions(Integer id)
	{
		Optional<Quiz> quiz = quizdao.findById(id);
		List<Questions> questionsFromDB = quiz.get().getQuestion();
		List<QuizWrapper> questionsForUser = new ArrayList<>();
		for(Questions q : questionsFromDB)
		{
			QuizWrapper qw = new QuizWrapper(q.getQuestion_id(),q.getQuestion_text(),q.getOption_a(),q.getOption_b(),q.getOption_c(),q.getOption_d());
			questionsForUser.add(qw);
		}
		
	
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
	}
	
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizdao.findById(id).get();
        List<Questions> questions = quiz.getQuestion();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getCorrect_option()))
                right++;

            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}


