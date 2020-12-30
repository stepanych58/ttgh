package com.stbegradleapp.fixer;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.servises.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExperimentWithSpring {
    public static void main(String[] args) {
        ApplicationContext ctxt = new AnnotationConfigApplicationContext(StbeAppConfig.class);
        UserService userService = ctxt.getBean(UserService.class);
        FixerUser user = userService.createUser();
        System.out.println(user);
    }
}
