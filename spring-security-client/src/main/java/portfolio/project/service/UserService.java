package portfolio.project.service;

import portfolio.project.entity.User;
import portfolio.project.entity.VerificationToken;
import portfolio.project.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User getUser(Long id);

    User createUser(UserModel user);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerficationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
