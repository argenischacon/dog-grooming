package com.argenischacon.dto.dog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class DogFormDto {
    @NotBlank(message = "{dog.name.required}")
    @Size(max = 50, message = "{dog.name.size}")
    private String name;

    @NotBlank(message = "{dog.breed.required}")
    @Size(max = 50, message = "{dog.breed.size}")
    private String dogBreed;

    @NotBlank(message = "{dog.color.required}")
    @Size(max = 30, message = "{dog.color.size}")
    private String color;

    private boolean allergic;
    private boolean specialAttention;

    @Size(max = 255, message = "{dog.observations.max}")
    private String observations;

    @NotNull(message = "{dog.owner.required}")
    private Long ownerId;
}
