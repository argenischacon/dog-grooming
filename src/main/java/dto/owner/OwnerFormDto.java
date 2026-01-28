package dto.owner;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class OwnerFormDto {
    @NotBlank(message = "{owner.dni.required}")
    @Size(min = 6, max = 20, message = "{owner.dni.size}")
    private String dni;

    @NotBlank(message = "{owner.name.required}")
    @Size(max = 50, message = "{owner.name.size}")
    private String name;

    @NotBlank(message = "{owner.lastname.required}")
    @Size(max = 50, message = "{owner.lastname.size}")
    private String lastname;

    @Min(value = 18, message = "{owner.age.min}")
    @Max(value = 120, message = "{owner.age.max}")
    private int age;

    @NotBlank(message = "{owner.phone.required}")
    @Size(max = 20, message = "{owner.phone.size}")
    private String phone;

    @Email(message = "{owner.email.invalid}")
    @Size(max = 100, message = "{owner.email.size}")
    private String email;
}
