package Model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import Dto.ResturantDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private userrole role;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "user_favourites", joinColumns = @JoinColumn(name = "user_id"))
	private List<ResturantDto> favorites = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<Address> addresses = new ArrayList<>();
}
