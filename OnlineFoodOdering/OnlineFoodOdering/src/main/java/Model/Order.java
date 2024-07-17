package Model;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User customer;

	@JsonIgnore
	@ManyToOne
	private Resturant resturant;

	private Long totalAmount;
	private String orderStatus;
	private Date createdAt;

	@ManyToOne
	private Address deliveryAddress;

	@OneToMany
	@JoinColumn(name = "order_id")
	private List<Orderitems> items;

	private int totalItem;
	private Long totalPrice;
}
