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
public class DogListDto {
    private Long id;
    private String name;
    private String dogBreed;
    private String color;

    private Long ownerId;
    private String ownerName;
}
