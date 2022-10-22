package portfolio.project.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import portfolio.project.entity.User;
import portfolio.project.event.RegistrationCompleteEvent;
import portfolio.project.service.UserService;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        // Send main to user
        String url = event.getApplicationUrl()
                    + "/api/user/verifyRegistration?token="
                    + token;
        // sendVerificationEmail()
        log.info("Click the link to verify the account: {}", url);
    }
}
