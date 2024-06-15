package model;

import lombok.Data;

@Data
public class QuizWrapper {
	
	private Integer question_id;
	private String question_text;
	private String option_a;
	private String option_b;
	private String option_c;
	private String option_d;
	
	
	public QuizWrapper(Integer question_id, String question_text, String option_a, String option_b, String option_c,
			String option_d) {
		super();
		this.question_id = question_id;
		this.question_text = question_text;
		this.option_a = option_a;
		this.option_b = option_b;
		this.option_c = option_c;
		this.option_d = option_d;
	}
	
	

}
