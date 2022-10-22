package portfolio.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.project.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
