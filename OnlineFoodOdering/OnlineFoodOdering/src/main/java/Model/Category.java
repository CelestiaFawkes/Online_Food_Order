package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private String name;
	
	@ManyToOne
	@JsonIgnore
	private Resturant resturant;
	
	
	

}
