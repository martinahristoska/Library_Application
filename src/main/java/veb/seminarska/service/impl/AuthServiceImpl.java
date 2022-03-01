package veb.seminarska.service.impl;


import org.springframework.stereotype.Service;
import veb.seminarska.model.User;
import veb.seminarska.model.exceptions.InvalidArgumentsException;
import veb.seminarska.model.exceptions.InvalidUserCredentialsException;
import veb.seminarska.repository.jpa.UserRepository;
import veb.seminarska.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

}
