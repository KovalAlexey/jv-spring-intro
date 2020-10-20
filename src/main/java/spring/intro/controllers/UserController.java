package spring.intro.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.intro.dto.UserResponseDto;
import spring.intro.model.User;
import spring.intro.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inject")
    public void injectUsers() {
        userService.add(new User("Bob", 25));
        userService.add(new User("Alex", 24));
        userService.add(new User("Alice", 21));
        userService.add(new User("Jesse", 18));
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.listUsers().stream()
                .filter(userFromDb -> userFromDb.getId().equals(userId))
                .findFirst();
        return getUserDto(user.get());
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.listUsers().stream()
                .map(this::getUserDto)
                .collect(Collectors.toList());
    }

    private UserResponseDto getUserDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        return userDto;
    }
}
