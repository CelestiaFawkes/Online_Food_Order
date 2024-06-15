package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.QuizWrapper;
import model.Response;
import services.QuizServices;


@RestController
@RequestMapping("quiz")
public class QuizController {
	
	
	@Autowired
	QuizServices quizservice;
	
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizservice.createquiz(category, numQ, title);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuizWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizservice.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizservice.calculateResult(id, responses);
    }


}
