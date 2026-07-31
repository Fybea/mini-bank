package fybea.service;

import fybea.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class UserService {
    private final Map<Integer, User> userMap;
    private int id = 0;

    public UserService(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public void createUser(String login) {
        id++;
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            if (entry.getValue().getLogin().equals(login)) {
                throw new IllegalArgumentException("User with this name: %s, already exist".formatted(login));
            }
        }
        User user = new User(id, login, new ArrayList<>());
        System.out.println("User created");
        userMap.put(user.getId(), user);
    }

    public void showAllUsers() {
        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public User showUser(int userId) {
        if (!userMap.containsKey(userId)) {
            throw new IllegalArgumentException("User with this id: %s, not exist".formatted(userId));
        }
        return userMap.get(userId);
    }
}
