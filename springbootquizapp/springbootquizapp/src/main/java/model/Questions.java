package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Questions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer question_id;
	private String question_text;
	private String option_a;
	private String option_b;
	private String option_c;
	private String option_d;
	private String correct_option;
	private String category;

}
