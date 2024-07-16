package Model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", allocationSize = 1)
public class Orderitems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Food food;
    
    private int quantity;
    
    @ElementCollection
    @CollectionTable(name = "order_item_ingredients", joinColumns = @JoinColumn(name = "order_item_id"))
    @Column(name = "ingredient")
    private List<String> ingredients;
    
    private Long totalItems;
}