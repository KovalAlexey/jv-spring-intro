package spring.intro;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.intro.config.AppConfig;
import spring.intro.model.User;
import spring.intro.service.UserService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        User alex = new User("Alex", 25);
        User bob = new User("Bob", 28);
        User alice = new User("Alice", 21);

        userService.add(alex);
        userService.add(bob);
        userService.add(alice);

        userService.listUsers().forEach(System.out::println);
    }
}
