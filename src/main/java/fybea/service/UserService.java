package fybea.service;

import fybea.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserService {
    private final Map<Integer, User> userMap;

    public UserService(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public User createUser(String login) {
        if (userMap.containsKey(login)) {
            throw new RuntimeException("Пользователь с таким именем уже существует"); // временная ошибка(переделать)
        } else {
            return new User(1, login, null);
        }
    }

    public void showAllUsers() {
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
