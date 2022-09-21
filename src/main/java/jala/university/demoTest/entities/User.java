package jala.university.demoTest.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank(message = "user FirstName must not be empty")
    @Size(min = 2, max = 255, message = "The name ${validatedValue} must be between {min} and {max} characters long")
    private String firstName;

    @NotBlank(message = "user LastName must not be empty")
    @Size(min = 2, max = 255, message = "The name ${validatedValue} must be between {min} and {max} characters long")
    private String lastName;

    @Pattern(
            regexp = "^[A-Za-z]+\\.[a-zA-Z0-9]+@(fundacion\\-jala\\.org)$",
            message = "Email is not in the correct format")
    @Column(unique = true)
    private String email;
}
