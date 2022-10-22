package portfolio.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.project.entity.User;
import portfolio.project.entity.VerificationToken;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
