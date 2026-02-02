package com.argenischacon.dto.owner;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

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

    @NotNull(message = "{owner.birthdate.required}")
    @Past(message = "{owner.birthdate.past}")
    private LocalDate birthdate;

    @NotBlank(message = "{owner.phone.required}")
    @Size(max = 20, message = "{owner.phone.size}")
    private String phone;

    @Email(message = "{owner.email.invalid}")
    @Size(max = 100, message = "{owner.email.size}")
    private String email;
}
