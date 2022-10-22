package portfolio.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.project.entity.User;
import portfolio.project.entity.VerificationToken;
import portfolio.project.event.RegistrationCompleteEvent;
import portfolio.project.model.PasswordModel;
import portfolio.project.model.UserModel;
import portfolio.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    private String applicationUrl (HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
    }


    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerficationToken(oldToken);

        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request),  verificationToken);
        return "Verification Link Sent";
    }



    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.createUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }



    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")) {
            return "User verified successfully";
        }
        return "Bad user";

    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user= userService.findUserByEmail(passwordModel.getEmail());
        String url = "";

        if(user != null) {
            String token = UUID.randomUUID().toString();

            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }

        return url;
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl
                + "/saveRegistration?token="
                + token;

        log.info("Click the link to reset your password: {}", url);

        return url;
    }
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel) {
        String result = userService.validatePasswordResetToken(token);

        if(!result.equalsIgnoreCase("valid")) {
            return "Invalid Token";
        }


        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        } else {
            return "Invalid token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
          User user = userService.findUserByEmail(passwordModel.getEmail());
          if(!userService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
              return "Invalid Password";
            }
            //Save New Password
            userService.changePassword(user, passwordModel.getNewPassword());

        return "Password changed successfully";
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken ) {
        String url = applicationUrl
                    + "/verifyRegistration?token="
                    + verificationToken.getToken();

        log.info("Click the link to verify your account: {}", url);
    }


}
