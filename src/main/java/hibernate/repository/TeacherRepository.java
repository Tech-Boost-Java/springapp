package hibernate.repository;

import hibernate.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.firstName = ?1 AND t.lastName = ?2")
    Optional<Teacher> findBFirstNameAndLastName(String lastName);

    Optional<Teacher> findByEmail(String email);
}
