package hibernate.repository;


import hibernate.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTitle(String title);

    Optional<Course> findByTeacherId(Long teacherId);
}
