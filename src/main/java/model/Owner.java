package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String dni;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @Min(0)
    private int age;

    private String phone;

    @Email
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dog> dogs = new ArrayList<>();

    // Helper methods for bidirectional relationship
    public void addDog(Dog dog) {
        dogs.add(dog);
        dog.setOwner(this);
    }

    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.setOwner(null);
    }
}
