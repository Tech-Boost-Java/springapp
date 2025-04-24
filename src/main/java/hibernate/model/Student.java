package hibernate.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "First name must start with a capital letter and contain only letters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "Last name must start with a capital letter and contain only letters")
    private String lastName;

    @Email
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Column(name = "group_name")
    @NotNull(message = "Group cannot be null")
    private String group;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

}
