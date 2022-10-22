package portfolio.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
