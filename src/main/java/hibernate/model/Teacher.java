package hibernate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID cannot be null")
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

    @Column(name = "position")
    @NotBlank(message = "Position cannot be empty")
    private String position;

    @Column(name = "department")
    @NotBlank(message = "Department cannot be empty")
    private String department;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

}