package Dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ResturantDto {

	private String title;

	@Column(length = 1000)
	private List<String> images;

	private String description;
	private Long Id;

}
