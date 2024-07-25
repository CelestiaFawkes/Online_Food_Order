package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resturant")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "resturant_seq", sequenceName = "resturant_seq", allocationSize = 1)
public class Resturant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private User owner;
    
    private String restName;
    private String description;
    private String cuisineType;
    
    @ManyToOne
    private Address address;
    
    @EmbeddedId
    private Contactinformation contactInformation;
    
    private String openingHours;
    
    @OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "resturant_images", joinColumns = @JoinColumn(name = "resturant_id"))
    @Column(name = "image_url")
    private List<String> images;
    
    private LocalDateTime registrationDate;
    private boolean open;
    
    @JsonIgnore
    @OneToMany(mappedBy = "resturant", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();
}