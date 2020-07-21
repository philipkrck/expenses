package de.pomc.expenses.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void init() {
        createUser("Philip");
        createUser("Malte");
        createUser("Olli");
        createUser("Christian");
    }

    private void createUser(String name) {
        userRepository.save(new User(name));
    }

    public List<User> findAll() { return userRepository.findAll(); }

}
