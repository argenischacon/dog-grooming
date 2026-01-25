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

    @NotBlank(message = "{owner.dni.required}")
    @Column(unique = true)
    private String dni;

    @NotBlank(message = "{owner.name.required}")
    private String name;

    @NotBlank(message = "{owner.lastname.required}")
    private String lastname;

    @Min(value = 0, message = "{owner.age.min}")
    private int age;

    private String phone;

    @Email(message = "{owner.email.invalid}")
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
