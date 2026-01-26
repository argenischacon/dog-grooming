package dto.owner;

import dto.dog.DogListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OwnerDetailDto {
    private Long id;
    private String dni;
    private String name;
    private String lastname;
    private int age;
    private String phone;
    private String email;

    private List<DogListDto> dogs;
}
