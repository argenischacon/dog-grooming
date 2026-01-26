package dto.dog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DogDetailDto {
    private Long id;
    private String name;
    private String dogBreed;
    private String color;
    private boolean allergic;
    private boolean specialAttention;
    private String observations;

    private Long ownerId;
    private String ownerName;
}
