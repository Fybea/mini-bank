package fybea.service;

import fybea.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserService {
    private final Map<Integer, User> userMap;

    public UserService(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public User createUser(String login) {
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            if (entry.getValue().getLogin().equals(login)) {
                System.out.println("Пользователь с таким именем уже существует, повторите команду.");
                return null;
            }
        }
        User user = new User(ThreadLocalRandom.current().nextInt(1, 101), login, new ArrayList<>()); // РЕАЛИЗОВАТЬ ID
        System.out.println("User created");
        userMap.put(user.getId(), user);
        System.out.println(user.getId());
        return user;
    }

    public void showAllUsers() {
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public User showUser(int userId) {
     return userMap.get(userId);
    }
}
